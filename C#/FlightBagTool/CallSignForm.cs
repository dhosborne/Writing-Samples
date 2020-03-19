using System;
using System.Windows.Forms;

namespace FlightBagTool
{
    public partial class CallSignForm : Form
    {
        private string[] prefixes = { "OMAHA", "ANX" };

        public CallSignForm()
        {
            InitializeComponent();
            for (int i = 0; i < prefixes.Length; i++)
            {
                this.prefixComboBox.Items.Add(this.prefixes[i]);
            }
        }

        private void SuffixBox_Click(object sender, EventArgs e)
        {
            this.SuffixBox.Text = GetUserInput();
        }

        private string GetUserInput()
        {
            using (var form = new KeypadForm())
            {
                // show the keypad from
                form.StartPosition = FormStartPosition.CenterParent;
                var result = form.ShowDialog();


                if (result == DialogResult.OK)
                {
                    // get the user input data from keypad form and return it
                    string userInput = form.ReturnCode;
                    return userInput;
                }
                else
                {
                    // nothing was entered, return null
                    return null;
                }
            }
        }

        private void cancelButton_Click(object sender, EventArgs e)
        {
            this.DialogResult = DialogResult.Cancel;
            this.Close();
        }

        private void okButton_Click(object sender, EventArgs e)
        {
            if (this.prefixComboBox.Text != "" && this.SuffixBox.Text != "")
            {
                string newCallSign = this.prefixComboBox.Text + "-" + this.SuffixBox.Text;
                // TODO add logic to alphabetize callSigns before saving
                Properties.Settings.Default.callSigns += ("," + newCallSign);
                Properties.Settings.Default.Save();
                this.DialogResult = DialogResult.OK;
                this.Close();
            }
        }
    }
}
