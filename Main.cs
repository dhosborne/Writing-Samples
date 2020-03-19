using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SQLite;
using System.Windows.Forms;

namespace BatteryStation
{
    public partial class Main : Form
    {
        private string partCell = "partnumberDGVC";
        private string revisionCell = "revisionDGVC";
        private string serialCell = "serialnumberDGVC";

        string discharge1Text = "Discharge 1";
        string chargeText = "Charging";
        string discharge2Text = "Discharge 2";
        string reconText = "Recon";
        string completeText = "Complete";

        private SQLiteConnection m_dbConnection = new SQLiteConnection(Properties.Resources.DatabaseConnection);
        private DataSet batteryDS = new DataSet();
        private List<string> serialList = new List<string>();

        public Main()
        {
            InitializeComponent();
            this.Text = Application.ProductName + " " + String.Format("Version {0}", Application.ProductVersion);
            try
            {
                // try to open the database file
                m_dbConnection.Open();
            }
            catch (SQLiteException e)
            {
                // if the database doesnt already exist, then make the datbase
                if (e.Message.ToString().Equals("Unable to open the database file"))
                {
                    Create_Database();
                }
            }


            // load the database into the table
            PopulateBatteryList();

            GetSerialsList();

            // attach the batteries list to the select CB's
            LoadBatteryComboBoxes();

        }


        /// <summary>
        /// Populate battery List
        /// Populates the battery list in the battery tab
        /// </summary>
        private void PopulateBatteryList()
        {
            batteryDS.Clear();

            using (SQLiteConnection conn = new SQLiteConnection(Properties.Resources.DatabaseConnection))
            {
                using (SQLiteCommand comm = new SQLiteCommand(conn))
                {
                    conn.Open();
                    var da = new SQLiteDataAdapter(Properties.Resources.GetAllBatteries, conn);
                    da.Fill(batteryDS);
                    batteriesDGV.ReadOnly = true;
                    batteriesDGV.DataSource = batteryDS.Tables[0].DefaultView;
                }
            }
        }

        private void LoadBatteryComboBoxes()
        {

            // station 1
            PopulateComboBox(s1BattComboBoxA);
            PopulateComboBox(s1BattComboBoxB);



            // station 2
            PopulateComboBox(s2BattComboBoxA);
            PopulateComboBox(s2BattComboBoxB);



            // station 3
            PopulateComboBox(s3BattComboBoxA);

            PopulateComboBox(s3BattComboBoxB);

            // station 4

            PopulateComboBox(s4BattComboBoxA);

            PopulateComboBox(s4BattComboBoxB);


            // station 5
            PopulateComboBox(s5BattComboBoxA);

            PopulateComboBox(s5BattComboBoxB);


            // station 6
            PopulateComboBox(s6BattComboBoxA);

            PopulateComboBox(s6BattComboBoxB);

        }

        private void PopulateComboBox(ComboBox box)
        {
            box.Items.Clear();

            foreach (var i in serialList)
            {
                box.Items.Add(i);
            }

        }

        private void GetSerialsList()
        {
            serialList.Clear();

            foreach (DataGridViewRow i in batteriesDGV.Rows)
            {
                serialList.Add(i.Cells[2].Value.ToString());
            }
        }

        /// <summary>
        /// Create Database
        /// Creates the SQLite3 database in the operating directory
        /// </summary>
        private void Create_Database()
        {
            SQLiteConnection.CreateFile(Properties.Resources.BatteryDatabaseName);

            using (SQLiteConnection conn = new SQLiteConnection(Properties.Resources.DatabaseConnection))
            {
                using (SQLiteCommand comm = new SQLiteCommand(conn))
                {
                    conn.Open();
                    comm.CommandText = Properties.Resources.CreateBatteriesTable;
                    comm.ExecuteNonQuery();
                    conn.Close();
                }
            }
        }

        /// <summary>
        /// Edit Button Click
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void EditButton_Click(object sender, EventArgs e)
        {
            if (batteriesDGV.SelectedRows.Count > 0)
            {
                BatteryForm child = new BatteryForm
                {
                    PartNumber = batteriesDGV.SelectedRows[0].Cells[partCell].Value.ToString(),
                    Revision = batteriesDGV.SelectedRows[0].Cells[revisionCell].Value.ToString(),
                    SerialNumber = batteriesDGV.SelectedRows[0].Cells[serialCell].Value.ToString(),
                    edit = true
                };

                child.FormClosed += new FormClosedEventHandler(AddBatteryForm_Closed);
                child.Show();
            }
        }


        /// <summary>
        /// Exit Tool Strip Menu Item
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void ExitToolStripMenuItem_Click(object sender, EventArgs e)
        {
            Close();
        }
        private void aboutToolStripMenuItem_Click(object sender, EventArgs e)
        {
            AboutBox child = new AboutBox();
            child.Show();
        }
        private void Form1_Load(object sender, EventArgs e)
        {
            //TODO

        }
        private void AddBatteryButton_Click(object sender, EventArgs e)
        {
            //TODO:
            BatteryForm child = new BatteryForm();
            child.FormClosed += new FormClosedEventHandler(AddBatteryForm_Closed);
            child.Show();


        }
        private void DeleteBatteryButton_Click(object sender, EventArgs e)
        {
            //TODO: Delete battery from SQLite Batteries Table
            if (batteriesDGV.SelectedRows.Count > 0)
            {
                Battery batt = new Battery(
                    batteriesDGV.SelectedRows[0].Cells[partCell].Value.ToString(),
                    batteriesDGV.SelectedRows[0].Cells[revisionCell].Value.ToString(),
                    batteriesDGV.SelectedRows[0].Cells[serialCell].Value.ToString()
                );
                batt.DeleteBattery();

            }

            HandleBatteryListChange();
        }
        private void AddBatteryForm_Closed(object sender, FormClosedEventArgs e)
        {
            HandleBatteryListChange();
        }
        private void HandleBatteryListChange()
        {
            PopulateBatteryList();
            GetSerialsList();
            LoadBatteryComboBoxes();
        }
        private void Check_Handler(String text, Label opLabel, Label timeLabel)
        {
            var time = DateTime.Now;
            opLabel.Text = text;
            timeLabel.Text = time.ToString();

        }

        #region Click Handlers
        /**
         Station 1 Handlers     
        */
        private void S1dc1CB_CheckedChanged(object sender, EventArgs e)
        {
            
            Check_Handler(discharge1Text, opLabel1, dateTimeLabel1);
        }
        private void S1cCB_CheckedChanged(object sender, EventArgs e)
        {
            Check_Handler(chargeText, opLabel1, dateTimeLabel1);
        }
        private void S1dc2CB_CheckedChanged(object sender, EventArgs e)
        {
            Check_Handler(discharge2Text, opLabel1, dateTimeLabel1);
        }
        private void S1RCCB_CheckedChanged(object sender, EventArgs e)
        {
            Check_Handler(reconText, opLabel1, dateTimeLabel1);
        }
        private void S1compCB_CheckedChanged(object sender, EventArgs e)
        {
            Check_Handler(completeText, opLabel1, dateTimeLabel1);
            s1BattComboBoxA.SelectedIndex = -1;
            s1BattComboBoxB.SelectedIndex = -1;
            s1dc1CB.Checked = false;
            s1cCB.Checked = false;
            s1dc2CB.Checked = false;
            s1RCCB.Checked = false;
            s1compCB.Checked = false;

        }
        private void S1BattA_SelectionChanged(object sender, EventArgs e)
        {
            Check_Handler("NEW", opLabel1, dateTimeLabel1);
        }
        private void S1BattB_SelectionChanged(object sender, EventArgs e)
        {
            Check_Handler("NEW", opLabel1, dateTimeLabel1);
        }

        /**
         Station 2 Handlers
             
             */
        private void S2dc1CB_CheckedChanged(object sender, EventArgs e)
        {
            Check_Handler(discharge1Text, opLabel2, dateTimeLabel2);

        }
        private void S2cCB_CheckedChanged(object sender, EventArgs e)
        {
            Check_Handler(chargeText, opLabel2, dateTimeLabel2);
        }
        private void S2dc2CB_CheckedChanged(object sender, EventArgs e)
        {
            Check_Handler(discharge2Text, opLabel2, dateTimeLabel2);
        }
        private void S2RCCB_CheckedChanged(object sender, EventArgs e)
        {
            Check_Handler(reconText, opLabel2, dateTimeLabel2);
        }
        private void S2compCB_CheckedChanged(object sender, EventArgs e)
        {
            Check_Handler(completeText, opLabel2, dateTimeLabel2);
            s2BattComboBoxA.SelectedIndex = -1;
            s2BattComboBoxB.SelectedIndex = -1;
            s2dc1CB.Checked = false;
            s2cCB.Checked = false;
            s2dc2CB.Checked = false;
            s2RCCB.Checked = false;
            s2compCB.Checked = false;
        }
        private void S2BattA_SelectionChanged(object sender, EventArgs e)
        {
            Check_Handler("NEW", opLabel2, dateTimeLabel2);
        }
        private void S2BattB_SelectionChanged(object sender, EventArgs e)
        {
            Check_Handler("NEW", opLabel2, dateTimeLabel2);
        }

        /**
            Station 3 Handlers
        */

        private void S3dc1CB_CheckedChanged(object sender, EventArgs e)
        {
            Check_Handler(discharge1Text, opLabel3, dateTimeLabel3);
        }
        private void S3cCB_CheckedChanged(object sender, EventArgs e)
        {
            Check_Handler(chargeText, opLabel3, dateTimeLabel3);
        }
        private void S3dc2CB_CheckedChanged(object sender, EventArgs e)
        {
            Check_Handler(discharge2Text, opLabel3, dateTimeLabel3);
        }
        private void S3RCCB_CheckedChanged(object sender, EventArgs e)
        {
            Check_Handler(reconText, opLabel3, dateTimeLabel3);
        }
        private void S3compCB_CheckedChanged(object sender, EventArgs e)
        {
            Check_Handler(completeText, opLabel3, dateTimeLabel3);
            s3BattComboBoxA.SelectedIndex = -1;
            s3BattComboBoxB.SelectedIndex = -1;
            s3dc1CB.Checked = false;
            s3cCB.Checked = false;
            s3dc2CB.Checked = false;
            s3RCCB.Checked = false;
            s3compCB.Checked = false;
        }
        private void S3BattA_SelectionChanged(object sender, EventArgs e)
        {
            Check_Handler("NEW", opLabel3, dateTimeLabel3);
        }
        private void S3BattB_SelectionChanged(object sender, EventArgs e)
        {
            Check_Handler("NEW", opLabel3, dateTimeLabel3);
        }


        /**
            Station 4 Handlers
     
        */
        private void S4dc1CB_CheckedChanged(object sender, EventArgs e)
        {
            Check_Handler(discharge1Text, opLabel4, dateTimeLabel4);
        }
        private void S4cCB_CheckedChanged(object sender, EventArgs e)
        {
            Check_Handler(chargeText, opLabel4, dateTimeLabel4);
        }
        private void S4dc2CB_CheckedChanged(object sender, EventArgs e)
        {
            Check_Handler(discharge2Text, opLabel4, dateTimeLabel4);
        }
        private void S4RCCB_CheckedChanged(object sender, EventArgs e)
        {
            Check_Handler(reconText, opLabel4, dateTimeLabel4);
        }
        private void S4compCB_CheckedChanged(object sender, EventArgs e)
        {
            Check_Handler(completeText, opLabel4, dateTimeLabel4);
            s4BattComboBoxA.SelectedIndex = -1;
            s4BattComboBoxB.SelectedIndex = -1;
            s4dc1CB.Checked = false;
            s4cCB.Checked = false;
            s4dc2CB.Checked = false;
            s4RCCB.Checked = false;
            s4compCB.Checked = false;
        }
        private void S4BattA_SelectionChanged(object sender, EventArgs e)
        {
            Check_Handler("NEW", opLabel4, dateTimeLabel4);
        }
        private void S4BattB_SelectionChanged(object sender, EventArgs e)
        {
            Check_Handler("NEW", opLabel4, dateTimeLabel4);
        }


        /**
             Station 5 Handlers
     
        */
        private void S5dc1CB_CheckedChanged(object sender, EventArgs e)
        {
            Check_Handler(discharge1Text, opLabel5, dateTimeLabel5);
        }
        private void S5cCB_CheckedChanged(object sender, EventArgs e)
        {
            Check_Handler(chargeText, opLabel5, dateTimeLabel5);
        }
        private void S5dc2CB_CheckedChanged(object sender, EventArgs e)
        {
            Check_Handler(discharge2Text, opLabel5, dateTimeLabel5);
        }
        private void S5RCCB_CheckedChanged(object sender, EventArgs e)
        {
            Check_Handler(reconText, opLabel5, dateTimeLabel5);
        }
        private void S5compCB_CheckedChanged(object sender, EventArgs e)
        {
            Check_Handler(completeText, opLabel5, dateTimeLabel5);
            s5BattComboBoxA.SelectedIndex = -1;
            s5BattComboBoxB.SelectedIndex = -1;
            s5dc1CB.Checked = false;
            s5cCB.Checked = false;
            s5dc2CB.Checked = false;
            s5RCCB.Checked = false;
            s5compCB.Checked = false;
        }
        private void S5BattA_SelectionChanged(object sender, EventArgs e)
        {
            Check_Handler("NEW", opLabel5, dateTimeLabel5);
        }
        private void S5BattB_SelectionChanged(object sender, EventArgs e)
        {
            Check_Handler("NEW", opLabel5, dateTimeLabel5);
        }

        /**
            Station 6 Handlers
     
        */
        private void S6dc1CB_CheckedChanged(object sender, EventArgs e)
        {
            Check_Handler(discharge1Text, opLabel6, dateTimeLabel6);

        }
        private void S6cCB_CheckedChanged(object sender, EventArgs e)
        {
            Check_Handler(chargeText, opLabel6, dateTimeLabel6);
        }
        private void S6dc2CB_CheckedChanged(object sender, EventArgs e)
        {
            Check_Handler(discharge2Text, opLabel6, dateTimeLabel6);
        }
        private void S6RCCB_CheckedChanged(object sender, EventArgs e)
        {
            Check_Handler(reconText, opLabel6, dateTimeLabel6);
        }
        private void S6compCB_CheckedChanged(object sender, EventArgs e)
        {
            Check_Handler(completeText, opLabel6, dateTimeLabel6);
            s6BattComboBoxA.SelectedIndex = -1;
            s6BattComboBoxB.SelectedIndex = -1;
            s6dc1CB.Checked = false;
            s6cCB.Checked = false;
            s6dc2CB.Checked = false;
            s6RCCB.Checked = false;
            s6compCB.Checked = false;
        }
        private void S6BattA_SelectionChanged(object sender, EventArgs e)
        {
            Check_Handler("NEW", opLabel6, dateTimeLabel6);
        }
        private void S6BattB_SelectionChanged(object sender, EventArgs e)
        {
            Check_Handler("NEW", opLabel6, dateTimeLabel6);
        }
        #endregion

    }
}
