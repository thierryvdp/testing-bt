class Book:
    """
    A class to represent a book structure and manage its contents.
    """

    def __init__(self, structure):
        """
        Initializes the Book with a given structure.
        """
        self.structure = structure
        self.contents = {title: "" for title in self.flatten_structure(structure)}
        self.placeholders = {title: st.empty() for title in self.flatten_structure(structure)}

        st.markdown("## Working...")
        toc_columns = st.columns(4)
        self.display_toc(self.structure, toc_columns)
        st.markdown("---")
