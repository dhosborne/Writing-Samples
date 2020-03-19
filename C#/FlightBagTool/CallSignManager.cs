using System;
using System.Drawing;
using System.Windows.Forms;

namespace FlightBagTool
{
    public partial class CallSignManager : Form
    {

        public CallSignManager()
        {
            InitializeComponent();

            // disable the remove button until a value is selected in the combobox
            this.removeButton.Enabled = false;

            // populate the combobox with values in the properties
            var callsigns = Properties.Settings.Default.callSigns.Split(',');
            foreach (string callsign in callsigns)
            {
                this.callSignBox.Items.Add(callsign);
            }
        }


        // enable the remove button and paint it
        public void enableRemoveButton()
        {
            this.removeButton.BackColor = Color.Chartreuse;
            this.removeButton.Enabled = true;
        }

        private void removeButton_Click(object sender, EventArgs e)
        {
            if (this.callSignBox.SelectedIndex >= 0)
            {
                string list = "";
                this.callSignBox.Items.RemoveAt(this.callSignBox.SelectedIndex);
                foreach (string text in this.callSignBox.Items)
                {
                    if (text != "")
                    {
                        list += (text + ",");
                    }

                }

                Properties.Settings.Default.callSigns = list;
                Properties.Settings.Default.Save();
                this.DialogResult = DialogResult.OK;
                this.Close();
            }
        }

        private void callSignBox_SelectedIndexChanged(object sender, EventArgs e)
        {
            this.enableRemoveButton();
        }

        private void cancelButton_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void callSignBox_Click(object sender, EventArgs e)
        {
            this.callSignBox.DroppedDown = true;
        }
    }
}
