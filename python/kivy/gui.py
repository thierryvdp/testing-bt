from kivy.app import App
from kivy.uix.button import Button
from kivy.uix.boxlayout import BoxLayout
from kivy.uix.gridlayout import GridLayout
from kivy.uix.label import Label

class YourApp(App):
    
    def build(self):
        root_widget = BoxLayout(orientation='vertical')
        
        self.output_label = Label(text="tata",size_hint_y=1, font_size='20sp')

        button_symbols = ('1', '2', '3', '+',
                          '4', '5', '6', '-',
                          '7', '8', '9', '.',
                          '0', '*', '/', '=')

        button_grid = GridLayout(cols=4, size_hint_y=2)
        for symbol in button_symbols:
            bouton = Button(text=symbol)
            # bouton.bind(on_release=self.do_stuff())
            bouton.bind(on_release=lambda x:self.do_stuff(bouton._label.text))
            button_grid.add_widget(bouton)
            self.output_label.text = self.output_label.text+symbol

        root_widget.add_widget(self.output_label)
        root_widget.add_widget(button_grid)

        return root_widget

    def do_stuff(self, obj):
        self.output_label.text = self.output_label.text+obj

YourApp().run()