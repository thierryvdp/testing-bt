load_dotenv()

GROQ_API_KEY = os.environ.get("GROQ_API_KEY")
st.session_state.api_key = GROQ_API_KEY
st.session_state.groq = Groq()

# Initialize session state variables if they do not exist
if 'button_disabled' not in st.session_state:
    st.session_state.button_disabled = False

if 'button_text' not in st.session_state:
    st.session_state.button_text = "Generate Book"

def disable():
    st.session_state.button_disabled = True

def enable():
    st.session_state.button_disabled = False

def empty_st():
    st.empty()

st.markdown("""
    <style>
        ...
    </style>
""", unsafe_allow_html=True)
