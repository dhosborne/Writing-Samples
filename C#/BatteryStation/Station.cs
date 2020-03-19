using System.Data;
using System.Data.SQLite;

namespace BatteryStation
{
    public class Station
    {
        private string id { get; set; }
        private string BatteryA { get; set; }
        private string BatteryB { get; set; }
        private string State { get; set; }
        private string State_changed { get; set; }

        public Station(int id, string a, string b, string state, string state_changed)
        {
            BatteryA = a;
            BatteryB = b;
            State = state;
            State_changed = state_changed;

        }

        public Station() { }

        public void AddStation()
        {
            string query = @"INSERT INTO " + Properties.Resources.ChargingStationTableName +
                "(a, b, state, state_changed)" +
                "values ($a, $b, $state, $state_changed";
            using (SQLiteConnection conn = new SQLiteConnection(Properties.Resources.DatabaseConnection))
            {
                using (SQLiteCommand comm = new SQLiteCommand(query, conn))
                {
                    comm.Parameters.Add("$a", DbType.String).Value = BatteryA;
                    comm.Parameters.Add("$b", DbType.String).Value = BatteryB;
                    comm.Parameters.Add("$state", DbType.String).Value = State;
                    comm.Parameters.Add("$state_changed", DbType.String).Value = State_changed;
                    conn.Open();
                    _ = comm.ExecuteNonQuery();
                }
            }
        }


        public void UpdateStation()
        {
            string query = @"UPDATE" + Properties.Resources.ChargingStationTableName +
                " SET (battery_a=$a, battery_b=$b, state=$state, state_change=$state_change)" +
                "WHERE id=$id";

            using (SQLiteConnection conn = new SQLiteConnection(Properties.Resources.DatabaseConnection))
            {
                using (SQLiteCommand comm = new SQLiteCommand(query, conn))
                {
                    comm.Parameters.Add("$a", DbType.String).Value = BatteryA;
                    comm.Parameters.Add("$b", DbType.String).Value = BatteryB;
                    comm.Parameters.Add("$state", DbType.String).Value = State;
                    comm.Parameters.Add("$state_changed", DbType.String).Value = State_changed;
                    comm.Parameters.Add("$id", DbType.String).Value = id;
                    conn.Open();
                    _ = comm.ExecuteNonQuery();
                }
            }
        }
    }
}
