# pip install --upgrade google-api-python-client google-auth-httplib2 google-auth-oauthlib
from googleapiclient.discovery import build
from google_auth_oauthlib.flow import InstalledAppFlow
from google.auth.transport.requests import Request
import os, pickle, base64

# Définir les scopes (permissions)
SCOPES = ['https://www.googleapis.com/auth/gmail.readonly']

def get_gmail_service():
    creds = None
    # Le fichier token.pickle stocke les jetons d'accès et de rafraîchissement de l'utilisateur
    if os.path.exists('token.pickle'):
        with open('token.pickle', 'rb') as token:
            creds = pickle.load(token)
    # Si les identifiants ne sont pas valides, laissez l'utilisateur se connecter.
    if not creds or not creds.valid:
        if creds and creds.expired and creds.refresh_token:
            creds.refresh(Request())
        else:
            flow = InstalledAppFlow.from_client_secrets_file(
                'd:/API/google.json', SCOPES)
            creds = flow.run_local_server(port=0)
        # Sauvegarder les identifiants pour le prochain lancement
        with open('token.pickle', 'wb') as token:
            pickle.dump(creds, token)

    service = build('gmail', 'v1', credentials=creds)
    return service

def get_email_content_txt(service, message_id):
    # Récupère un email spécifique par son ID
    message = service.users().messages().get(userId='me', id=message_id, format='full').execute()
    
    # La partie du corps de l'email peut se trouver dans différentes parties du message,
    # selon qu'il s'agisse d'un email en texte brut ou HTML, ou multipart.
    # Cet exemple se concentre sur le texte brut.
    
    # Récupère le contenu du message dans le corps de l'email
    # Note : Ceci est une simplification; vous devrez peut-être gérer différents cas
    # tels que les messages multipart ou encodés.
    try:
        parts = message['payload']['parts']
        for part in parts:
            if part['mimeType'] == 'text/plain':
                data = part['body']['data']
                # Les données encodées en base64 doivent être décodées
                content = base64.urlsafe_b64decode(data).decode()
                return content
    except KeyError:
        # Gestion simplifiée des erreurs; ajustez selon vos besoins
        print("Impossible de trouver le contenu du texte brut.")
        return None
    
def get_email_content_html(service, message_id):
    # Récupère un email spécifique par son ID, en demandant le format 'full'
    message = service.users().messages().get(userId='me', id=message_id, format='full').execute()

    # Récupère le contenu HTML du message
    content_html = None
    try:
        parts = message['payload']['parts']
        for part in parts:
            if part['mimeType'] == 'text/html':
                data = part['body']['data']
                content_html = base64.urlsafe_b64decode(data).decode('utf-8')
                break  # Stoppe dès que la première partie HTML est trouvée
    except KeyError:
        print("Impossible de trouver le contenu HTML.")

    return content_html

def process_content(id,content):
    if content:
        print(f"Contenu de l'email ID {id}:")
        print(content)
        # Ici, vous pouvez effectuer un traitement supplémentaire sur le contenu de l'email
        with open('d:/tmp/log/mail_'+str(id)+'.txt', 'a') as fichier:
             fichier.write(content)


def search_emails(service, query):
    # Recherche des emails correspondant à la requête
    result = service.users().messages().list(userId='me', q=query).execute()
    messages = result.get('messages', [])

    if not messages:
        print("Aucun email trouvé.")
    else:
        print("Emails trouvés :")
        i=0
        for message in messages:
            i=i+1
            content = get_email_content_html(service, message['id'])
            if content:
                process_content(message['id'],content)
            else:
                content = get_email_content_txt(service, message['id'])
                if content:
                   process_content(message['id'],content)
                else:
                   print("message vide ?")

if __name__ == '__main__':
    service = get_gmail_service()
    search_query = 'subject:("Vous avez reçu"  AND "nouvelles offres")'  # Remplacez "Votre sujet ici" par le sujet recherché
    search_emails(service, search_query)
