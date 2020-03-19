using System;
using System.Windows.Forms;

namespace FlightBagTool
{
    public partial class KeypadForm : Form
    {
        private string displayString = "";
        public string ReturnCode { get; set; }

        public KeypadForm()
        {
            InitializeComponent();
        }

        private void DigitKey_Click(object sender, EventArgs e)
        {
            Button butt = (Button)sender;
            this.UpdateDisplay(butt.Text);
        }

        private void ClearButton_Click(object sender, EventArgs e)
        {
            // Clear the display box out
            this.UpdateDisplay("");
        }

        private void OkButton_Click(object sender, EventArgs e)
        {
            // returns the number in the number box
            this.ReturnCode = this.displayString;
            this.DialogResult = DialogResult.OK;
            this.Close();
        }

        private void UpdateDisplay(string addOn)
        {
            if (addOn == "")
            {
                // if the clear button was pressed just update to nothing
                this.displayString = "";
                this.displayBox.Text = "";
            }
            else
            {
                // concatinate the addOn value to the display box
                if (this.displayString.Length < 4)
                {
                    this.displayString += addOn;
                    this.displayBox.Text = this.displayString;
                }
                else
                {
                    MessageBox.Show("Input is limited to 4 digits", "Input too long");
                }
            }
        }

        private void CancelButton_Click(object sender, EventArgs e)
        {

            this.Close();
        }
    }
}
