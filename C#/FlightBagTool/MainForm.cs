using System;
using System.Drawing;
using System.Windows.Forms;

namespace FlightBagTool
{
    public partial class MainForm : Form
    {
        // hold the array of callsigns in Properties.Settings.Default
        private String[] callSigns;

        Color landingConfigColor = Color.Chartreuse;
        Color mceConfigColor = Color.Tomato;

        string gdtOnString = "GDT-TX\n ON";
        string gdtOffString = "GDT-TX\n OFF";

        string speedLvrBackString = "SPEED LVR\n BACK";
        string speedLvrHighString = "SPEED LVR\n HIGH";

        string lightsOffString = "Lights\n OFF";
        string lightsOnString = "Lights\n ON";

        string gdtAntWideString = "GDT Antenna\n WIDE";
        string gdtAntNarrowString = "GDT Antenna\n Narrow";

        public MainForm()
        {
            InitializeComponent();
            // populate the call signs list by converting string to array
            this.LoadCallSignsFromMemory();

            this.Text = Application.ProductName + " V." + Application.ProductVersion;

            this.gdtButton.Text = gdtOnString;
            this.speedLeverButton.Text = speedLvrHighString;
            this.lightsButton.Text = lightsOnString;
            this.gdtBeamButton.Text = gdtAntWideString;

        }

        private void Form1_Load(object sender, EventArgs e)
        {
            this.PopulateCallSignsComboBox();
            this.AddSegmentButtons();
            this.callSignComboBox.SelectedIndex = Properties.Settings.Default.selectedCall;
            this.sqawkBox.Text = Properties.Settings.Default.sqawkbox;
            this.altitudeBottomBox.Text = Properties.Settings.Default.altitudeBottomBox;
            this.altitudeTopBox.Text = Properties.Settings.Default.altitudeTopBox;
            this.rotateBox.Text = Properties.Settings.Default.rotateBox;
            this.liftOffBox.Text = Properties.Settings.Default.liftOffBox;
            this.approachBox.Text = Properties.Settings.Default.approachBox;
        }

        /*
            Helper Functions 
        */
        private void LoadCallSignsFromMemory()
        {
            this.callSigns = Properties.Settings.Default.callSigns.Split(',');
        }
        private void PopulateCallSignsComboBox()
        {
            for (int i = 0; i < this.callSigns.Length; i++)
            {
                // on form load, populate call signs box with each array element
                this.callSignComboBox.Items.Add(this.callSigns[i]);

            }
        }
        private void ClearCallSignComboBox()
        {
            this.callSignComboBox.Items.Clear();
            this.callSignComboBox.ResetText();
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
                else if (result == DialogResult.Cancel)
                {
                    return "cancelled";
                }
                else
                {
                    // nothing was entered, return null
                    return null;
                }
            }
        }


        private void TextBox_Click(object sender, EventArgs e)
        {
            TextBox tb = (TextBox)sender;
            string update = this.GetUserInput();

            if (update != "cancelled")
            {
                tb.Text = update;
                string name = tb.Name;

                Properties.Settings.Default[name] = update;
                Properties.Settings.Default.Save();
            }



        }




        /*
          On-Click Events
        */

        private void CallSignComboBox_Click(object sender, EventArgs e)
        {
            this.callSignComboBox.DroppedDown = true;
        }


        private void AddCallsignButton_Click(object sender, EventArgs e)
        {
            using (var form = new CallSignForm())
            {
                // show the callsign form
                form.StartPosition = FormStartPosition.CenterParent;
                var result = form.ShowDialog();

                if (result == DialogResult.OK)
                {
                    this.ClearCallSignComboBox();
                    this.LoadCallSignsFromMemory();
                    this.PopulateCallSignsComboBox();
                }

            }
        }

        private void SegmentButton_Click(object sender, EventArgs e)
        {
            Control button = ((Control)sender);

            if (button.BackColor == Color.DodgerBlue)
            {
                button.BackColor = Color.Transparent;
                button.ForeColor = Color.Black;
            }
            else
            {
                button.BackColor = Color.DodgerBlue;
                button.ForeColor = Color.White;
            }
        }

        private void GdtButton_Click(object sender, EventArgs e)
        {
            Control btn = ((Control)sender);
            if (btn.BackColor == Color.Chartreuse)
            {
                btn.Text = gdtOffString;
                btn.BackColor = mceConfigColor;
            }
            else
            {
                btn.Text = gdtOnString;
                btn.BackColor = landingConfigColor;
                
            }
        }

        private void GearButton_Click(object sender, EventArgs e)
        {
            Control btn = ((Control)sender);

            if (btn.BackColor == Color.Chartreuse)
            {
                btn.Text = speedLvrBackString;
                btn.BackColor = mceConfigColor;
            }
            else
            {
                btn.Text = speedLvrHighString;
                btn.BackColor = landingConfigColor;

            }
        }

        private void LightsButton_Click(object sender, EventArgs e)
        {
            Control btn = ((Control)sender);

            if (btn.BackColor == Color.Chartreuse)
            {
                btn.Text = lightsOffString;
                btn.BackColor = mceConfigColor;
            }
            else
            {
                btn.Text = lightsOnString;
                btn.BackColor = landingConfigColor;
            }
        }
        private void gdtBeamButton_Click(object sender, EventArgs e)
        {
            Control btn = ((Control)sender);

            if (btn.BackColor == Color.Chartreuse)
            {
                btn.Text = gdtAntNarrowString;
                btn.BackColor = mceConfigColor;
            }
            else
            {
                btn.Text = gdtAntWideString;
                btn.BackColor = landingConfigColor;
            }
        }
        /*
            Menu Bar items 
        */
        private void CallSignManagerMenuItem_Click(object sender, EventArgs e)
        {
            using (var form = new CallSignManager())
            {
                // show the Call Sign Manager from
                form.StartPosition = FormStartPosition.CenterParent;
                var result = form.ShowDialog();

                if (result == DialogResult.OK)
                {
                    this.ClearCallSignComboBox();
                    this.LoadCallSignsFromMemory();
                    this.PopulateCallSignsComboBox();
                }

            }
        }

        private void ExitToolStripMenuItem_Click(object sender, EventArgs e)
        {
            this.Close();
        }



        /// <summary>
        /// Adds segmet buttons to panel based on values stored in memory
        /// </summary>
        private void AddSegmentButtons()
        {
            if (Properties.Settings.Default.sectors != "")
            {
                var list = Properties.Settings.Default.sectors.Split(',');

                foreach (string sector in list)
                {
                    Button sectorButton = new Button
                    {
                        Text = sector
                    };
                    sectorButton.Size = new Size(50, 50);
                    sectorButton.Click += this.SegmentButton_Click;
                    this.segmentsPanel.Controls.Add(sectorButton);
                }
            }
        }

        private void SegmentListToolStripMenuItem_Click(object sender, EventArgs e)
        {
            using (var form = new SegmentsForm())
            {
                // show the callsign form
                form.StartPosition = FormStartPosition.CenterParent;
                var result = form.ShowDialog();

                if (result == DialogResult.OK)
                {
                    //TODO: update segment list
                    // empty segments control panel
                    this.segmentsPanel.Controls.Clear();

                    // reload segmets control panel
                    this.AddSegmentButtons();
                }
                else
                {
                    MessageBox.Show("Nothing to do", "Dialog Cancelled");
                }

            }
        }

        private void AboutToolStripMenuItem1_Click(object sender, EventArgs e)
        {
            var about = new AboutBox1();
            about.Show();
        }

        private void callSignComboBox_SelectedValueChanged(object sender, EventArgs e)
        {
            int index = callSignComboBox.SelectedIndex;
            Properties.Settings.Default.selectedCall = index;
            Properties.Settings.Default.Save();
        }

        /// <summary>
        /// Clear all controls
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void newMissionToolStripMenuItem_Click(object sender, EventArgs e)
        {
            this.callSignComboBox.Text = "";
            this.sqawkBox.Text = "";

            this.gdtButton.BackColor = landingConfigColor;
            this.gdtButton.Text = gdtOnString;

            this.speedLeverButton.Text = speedLvrHighString;
            this.speedLeverButton.BackColor = landingConfigColor;

            this.lightsButton.Text = lightsOnString;
            this.lightsButton.BackColor = landingConfigColor;

            this.gdtBeamButton.Text = gdtAntWideString;
            this.gdtBeamButton.BackColor = landingConfigColor;

            this.altitudeTopBox.Text = "";
            this.altitudeBottomBox.Text = "";

            this.segmentsPanel.Controls.Clear();

            this.AddSegmentButtons();

            this.rotateBox.Text = "";
            this.liftOffBox.Text = "";
            this.approachBox.Text = "";
        }

    }
}
