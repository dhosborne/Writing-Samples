using System;
using System.Data;
using System.Data.SQLite;
using System.Windows.Forms;

namespace BatteryStation
{
    public class Battery
    {
        public string part_number { get; set; }
        public string revision { get; set; }
        public string serial_number { get; set; }
        public bool is_active { get; set; }

        public Battery(string part_number, string revision, string serial_number)
        {
            this.part_number = part_number;
            this.revision = revision;
            this.serial_number = serial_number;
            is_active = true;
        }

        public Battery() { }

        public void Deactivate_Battery()
        {
            this.is_active = false;
        }

        public bool Check_ifActiveBattery()
        {
            return this.is_active;
        }

        public bool AddBattery()
        {
            string query = @"INSERT INTO " + Properties.Resources.BatteryTableName + 
                " (part_number, revision, serial_number, is_active) " +
                "values ($part_number, $revision, $serial_number, $is_active)";

            using (SQLiteConnection conn = new SQLiteConnection(Properties.Resources.DatabaseConnection))
            {
                using (SQLiteCommand comm = new SQLiteCommand(query, conn))
                {
                    comm.Parameters.Add("$part_number", DbType.String).Value = part_number;
                    comm.Parameters.Add("$revision", DbType.String).Value = revision;
                    comm.Parameters.Add("$serial_number", DbType.String).Value = serial_number;
                    comm.Parameters.Add("$is_active", DbType.Int32).Value = 1;
                    conn.Open();
                    try
                    {
                        _ = comm.ExecuteNonQuery();
                        return true;
                    }
                    catch (Exception e)
                    {
                        if(e.Message.Contains("serial_number is not unique"))
                        {
                            MessageBox.Show("Serial Number must be unique", "SQLite Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                        }
                        else
                        {
                            MessageBox.Show(e.Message, "SQLite Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
                        }
                    }

                    return false;
                }
            }

        }

        public void DeleteBattery()
        {
            string query = @"DELETE FROM " + Properties.Resources.BatteryTableName + 
                " WHERE serial_number=$serial_number";

            using (SQLiteConnection conn = new SQLiteConnection(Properties.Resources.DatabaseConnection))
            {
                using (SQLiteCommand comm = new SQLiteCommand(query, conn))
                {
                    comm.Parameters.Add("$serial_number", DbType.String).Value = serial_number;
                    conn.Open();
                    _ = comm.ExecuteNonQuery();
                }
            }
        }

        public void UpdateBattery()
        {
            string query = @"UPDATE " + Properties.Resources.BatteryTableName + 
                " SET part_number=$part_number, revision=$revision " +
                "WHERE serial_number=$serial_number";

            using (SQLiteConnection conn = new SQLiteConnection(Properties.Resources.DatabaseConnection))
            {
                using (SQLiteCommand comm = new SQLiteCommand(query, conn))
                {
                    comm.Parameters.Add("$part_number", DbType.String).Value = part_number;
                    comm.Parameters.Add("$revision", DbType.String).Value = revision;
                    comm.Parameters.Add("$serial_number", DbType.String).Value = serial_number;
                    conn.Open();
                    _ = comm.ExecuteNonQuery();
                }
            }
        }
    }
}
