using System;
using System.Windows.Forms;

namespace BatteryStation
{
    public partial class BatteryForm : Form
    {
        public string PartNumber
        {
            get {
                return partNumberCB.Text;
            }
            set { partNumberCB.SelectedIndex = partNumberCB.FindString(value); }
        }

        public string Revision
        {
            get { return revTB.Text.ToUpper();  }
            set { revTB.Text = value; }
        }

        public string SerialNumber
        {
            get { return snTB.Text.ToUpper();  }
            set { snTB.Text = value; }
        }

        public bool edit = false;
   
        public BatteryForm()
        {
            InitializeComponent();
        }

        private void CancelButton_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void SaveButton_Click(object sender, EventArgs e)
        {

            Battery batt = new Battery(PartNumber, Revision, SerialNumber);
            if(edit)
            {
                batt.UpdateBattery();
                this.Close();
            }
            else
            {
                if (batt.AddBattery())
                {
                    this.Close();
                }
            }
        }
    }
}
