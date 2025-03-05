import speech_recognition as sr
import pyttsx3
from typing import Any

class Voice :
    speech_engine: Any
    recognizer: Any
    source: Any    

    def __init__ (self, var1, var2, loc, opt="Valeur"):	
        self. variable1= var1
        self. Variable2= var2
        self.globale.append(loc)
        self.speech_engine = pyttsx3.init()
        self.recognizer = sr.Recognizer()
        self.source = sr.Microphone()
        
    def getAudio(self) :
        return self.recognizer.listen(self.source)
    
    def sayText(self, texte):
            self.speech_engine.say(texte)
#            self.speech_engine.runAndWait()

#    @classmethod
#    def methode_globale_index(cls,variable):



