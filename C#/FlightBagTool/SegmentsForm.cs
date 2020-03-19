using System;
using System.Windows.Forms;

namespace FlightBagTool
{
    public partial class SegmentsForm : Form
    {
        private string segmentsList = "";

        public SegmentsForm()
        {
            InitializeComponent();

            this.segmentsList = Properties.Settings.Default.sectors;
            this.SegmentsBox.Text = segmentsList;

        }

        private void SaveButton_Click(object sender, EventArgs e)
        {
            this.segmentsList = this.SegmentsBox.Text;
            Properties.Settings.Default.sectors = segmentsList;
            Properties.Settings.Default.Save();
            this.DialogResult = DialogResult.OK;
            this.Close();
        }
    }
}
