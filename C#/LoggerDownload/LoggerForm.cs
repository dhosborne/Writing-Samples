/*********************************************************
 *  Logger Download Program
 *  Created by: Derek H Osborne
 *  derek.osborne@ga-si.com
 *  
 *  Inital Date: Feb. 21st, 2019
 *  
 *  Windows application for downloading and purging log
 *  files on CBP MGCS PSO1 and PSO2 racks
 *  
 *  Change log:
 *  2/12/2019 - Initial Commit
 *  6/10/2019 - Fixed bug causing month to be recorded incorrectly when creating folders.
 *              Added validation for Flight and Tail number textboxes to only accept digits.
 *              Creted tool tip for flight and tail number textboxes.
 *              Added enable/disable button functions to prevent triggering download and purge events
 *              more than once before they are completed.
 * 6.14.2019  - Fixed purge button typo
 * ******************************************************/


using System;
using System.Collections.Generic;
using System.Windows.Forms;
using System.Net;
using System.IO;
using System.IO.Compression;
using System.Drawing;


namespace LoggerDownload
{
    public partial class LoggerForm : Form
    {

        // form variables
        private string title = Application.ProductName + " V." + Application.ProductVersion;

        // remote server information
        private string ftpID = "ga"; // default ftp id
        private string ftpPassword = "password"; // default ftp address
        private string remoteLogDir = "/%2Fsd1b/log"; // default log path on the racks
        private string ppo1IP = "141.248.172.152";
        private string ppo2IP = "141.248.172.153";

        // local directory information
        private string localDesktopDir = Environment.GetFolderPath(Environment.SpecialFolder.Desktop); // path to users desktop
        private string localDestination = "";  //path of the final location to copy loggers to

        // form information 
        private string tailNumber = ""; // provided by user
        private string flightNumber = ""; // provided by user
        private string currentDate = DateTime.Now.ToString("yyyyMMdd"); // current time stamp yearMONTHday format
        private string recordTitle = ""; // the concatenated title of the log folder to create tail_fight_date
        private Boolean fullDownload = false; // signal to download PPO1 and PPO2 logs

        // list variables
        private List<string> filesList = new List<string>(); // list to hold files that are listed from the rack


        public LoggerForm()
        {
            InitializeComponent();

            this.Text = Application.ProductName + " V." + Application.ProductVersion;
            // get the last stored path
            if (Properties.Settings.Default.destination != null)
            {
                this.directoryBox.Text = Properties.Settings.Default.destination;
            }
            

            ToolTip inputTip = new ToolTip
            {
                AutoPopDelay = 5000,
                InitialDelay = 500,
                ReshowDelay = 500,
                ShowAlways = true
            };
            inputTip.SetToolTip(this.tailNumberTextBox, "Numbers only");
            inputTip.SetToolTip(this.flightNumberTextBox, "Numbers only");


            ToolTip checkboxTip = new ToolTip
            {
                AutoPopDelay = 5000,
                InitialDelay = 500,
                ReshowDelay = 500,
                ShowAlways = true
            };
            checkboxTip.SetToolTip(this.allLogsCheckBox, "If checked, download loggers from all racks");

        }

        /// <summary>
        /// On click listener for download button
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void DownloadButton_Click(object sender, EventArgs e)
        {


            // empty file list for new run
            this.filesList.Clear();

            // Gather form data
            if (this.tailNumberTextBox.Text.Length > 0)
            {
                this.tailNumber = this.tailNumberTextBox.Text;
            }

            if (this.flightNumberTextBox.Text.Length > 0)
            {
                this.flightNumber = this.flightNumberTextBox.Text;
            }

            if (this.allLogsCheckBox.Checked)
            {
                this.fullDownload = true;
            }

            this.UpdatePath(this.directoryBox.Text);

            // determine record directory name
            this.recordTitle = "CBP" +tailNumber + "_FLT" + flightNumber + "_" + this.currentDate; // format "CBPXXX_FLTXXX_yyyymmdd
            
            // determine the path where the record will be written
            if(Properties.Settings.Default.destination != "" && Directory.Exists(Properties.Settings.Default.destination))
            {
                this.localDestination = Properties.Settings.Default.destination + "\\" + this.recordTitle; // (supplied Directory)\(record Title)
            }
            else
            {
                this.localDestination = this.localDesktopDir + "\\" + this.recordTitle + "\\";  // %USERDIR%\Desktop\(recordTitle)\
            }
            
            
            this.InformUser("Folder Name= " + recordTitle + "\n");


            try
            {
                // disable the download and purge buttons once download button is pressed.
                this.DisableButtons();

                //get the list of file from PPO1
                this.InformUser("Attempting to contact PPO1 @ " + ppo1IP + "\n");
                

                if(this.GetFileList(ppo1IP))
                {
                    // create the local download directory if it does not already exist
                    var destination = this.localDestination + "\\PPO1\\";
                    FileInfo desktopDir = new FileInfo(destination);
                    desktopDir.Directory.Create();
                    
                    // request list of files on PSO1, then loop over list and download
                    this.InformUser("Downloading Files from PPO1\n");
                    

                    if (this.filesList.Count > 0)
                    {
                        foreach (string file in this.filesList)
                        {
                            if (file != "" && file != "." && file != "..") // dont download navigation dir items
                            {
                                this.InformUser("Downloading: " + file + "...");
                                this.DownloadFile(file, destination, this.ppo1IP);
                            }

                        }
                    }
                }


                // Download PSO2 logs if user selected All logs
                if (this.fullDownload)
                {
                    // empty the file list 
                    this.filesList.Clear();


                    this.InformUser("Attempting to contact PPO2 @ " + ppo2IP + " \n");
                    


                    // get a list of logs on PSO2 then loop over the list downloading the files
                    if (this.GetFileList(ppo2IP))
                    {
                        // create a separate directory for PPO2, in the local folder
                        var destination = this.localDestination + "\\PPO2\\";
                        FileInfo desktopDir = new FileInfo(destination);
                        desktopDir.Directory.Create();


                        this.InformUser("Downloading Files from PPO2 \n");
                        

                        if (this.filesList.Count > 0)
                        {
                            foreach (string file in this.filesList)
                            {
                                if(file != "" && file != "." && file != "..") // dont download nav items 
                                {
                                    this.InformUser("Downloading: " + file +"...");
                                    this.DownloadFile(file, destination, this.ppo2IP);
                                }

                            }
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "Logger Download Failed");
            }
            finally
            {
                if(Directory.Exists(this.localDestination))
                {
                    // compress the destination directory
                    this.CompressDirectory();

                    // show the downloaded record
                    System.Diagnostics.Process.Start(this.localDestination);
                }

                // turn the buttons back on
                this.EnableButtons();
                
                // start the purge function
                this.PurgeRacks();
            }




        }

        /// <summary>
        /// Collect a list of files in the /sdb1/log file directory at the IP specified by the location argument
        /// </summary>
        /// <param name="location">
        ///     IPAddress of the rack being interrogated, should only contain the numerals of the rack ip
        /// </param>
        /// <returns>
        ///     returns true if able to get file list, otherwise false
        /// </returns>
        private bool GetFileList(string location)
        {
            this.filesList.Clear();
            try
            {
                var remoteLocation = "ftp://" + location + this.remoteLogDir; 
                this.InformUser("Getting file list from " + remoteLocation + "\n");
                



                // create ftp request object to get directory list
                FtpWebRequest request = (FtpWebRequest)WebRequest.Create(remoteLocation);
                request.Credentials = new NetworkCredential(ftpID, ftpPassword);
                request.Method = WebRequestMethods.Ftp.ListDirectory;
                request.UsePassive = true;
                request.UseBinary = false;
                request.KeepAlive = false;
                request.Proxy = null;

                // create response object and consume server response
                FtpWebResponse response = (FtpWebResponse)request.GetResponse();
                Stream responseStream = response.GetResponseStream();
                StreamReader reader = new StreamReader(responseStream);

                while(!reader.EndOfStream)
                {
                    this.filesList.Add(reader.ReadLine());
                }

                
                // dispose of streams
                reader.Close();
                response.Close();

                return true;
            }
            catch (Exception ex)
            {
                MessageBox.Show("Get File List Failed - " + ex.Message, location + " - Error");

                return false;
            }
        }

        /// <summary>
        /// Opens an ftp connection to the remote server location and downloads the specified file
        /// </summary>
        /// <param name="fileName">
        ///     The name of the file to be retreved. Ex. A0001.MAT
        /// </param>
        /// <param name="remoteLocation">
        ///     the ip address of the remote server location  
        /// </param>
        /// <param name="destination">
        ///     the local directory in which files will be saved
        /// </param>
        private void DownloadFile(string fileName, string destination, string remoteLocation)
        {

            try
            {
                // disable the download and purge buttons until operation is complete
                this.DisableButtons();

                // define the remote path to the file to be downloaded
                string uri = "ftp://" + remoteLocation + remoteLogDir + "/" + fileName;
                

                // create URI of the file to be downloaded and ensure it is an ftp schema
                Uri serverURI = new Uri(uri);
                if(serverURI.Scheme != Uri.UriSchemeFtp)
                {
                    return;
                }

                // create a ftp client and execute the request
                using (WebClient ftp = new WebClient())
                {
                    ftp.Credentials = new NetworkCredential(this.ftpID, this.ftpPassword);
                    ftp.DownloadFile(uri, destination + fileName);
                    this.InformUser("DONE \n");
                }
            }

            catch (Exception ex)
            {
                MessageBox.Show("Download failed", ex.Message);
                return;
            }
            finally
            {
                // re-enable all buttons
                this.EnableButtons();
            }
        }

        /// <summary>
        /// Delete the specified remote file from the specified location
        /// </summary>
        /// <param name="fileName">
        ///     Name of the file to be deleted. (Ex. A0001.MAT)
        /// </param>
        /// <param name="location">
        ///     The remote locations IP Address
        /// </param>
        /// <returns>
        ///     returns the status dialog from the request object
        /// </returns>
        private string DeleteFile(string fileName, string location)
        {
            try
            {
                var remoteLocation = "ftp://" + location + this.remoteLogDir + "/";
                this.InformUser("Delete " + remoteLocation + fileName + "\n");

                FtpWebRequest deleteReq = (FtpWebRequest)WebRequest.Create(remoteLocation + fileName);
                deleteReq.Method = WebRequestMethods.Ftp.DeleteFile;
                deleteReq.Credentials = new NetworkCredential(this.ftpID, this.ftpPassword);

                using (FtpWebResponse deleteRes = (FtpWebResponse)deleteReq.GetResponse())
                {
                    this.InformUser(" DONE\n");
                    return deleteRes.StatusDescription;
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Delete File Failed", ex.Message);
                return null;
            }

        }

        /// <summary>
        /// On-Click listener for purge button
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void PurgeButton_Click(object sender, EventArgs e)
        {
            this.PurgeRacks();
        }

        /// <summary>
        /// Purge Racks. Requests a list of all the logs, loops through the list calling the DeleteFile function
        /// Will only execute on positive confirmation of user dialog
        /// </summary>
        private void PurgeRacks()
        {
            DialogResult purgeResult = MessageBox.Show("Are you ready to purge all loggers from the racks?", "Logger Download Complete", MessageBoxButtons.YesNo);
            if (purgeResult == DialogResult.Yes)
            {
                
                if (this.GetFileList(this.ppo1IP))
                {
                    if(this.filesList.Count > 0)
                    {
                        foreach (string file in this.filesList)
                        {
                            if(file != "" && file != "." && file !="..") // dont delete nav items
                            {
                                this.DeleteFile(file, this.ppo1IP);
                            }
                        
                        }
                    }

                    this.InformUser("PPO1 Loggers purged\n");

                }


                
                if (this.GetFileList(this.ppo2IP))
                {
                    if(this.filesList.Count > 0)
                    {
                        foreach (string file in this.filesList)
                        {
                            if(file != "" && file != "." && file != "..") // dont delete nav items
                            {
                                this.DeleteFile(file, this.ppo2IP);
                            }
                        }
                    }
                    this.InformUser("PPO2 loggers purged\n");
                }
            }
            else
            {
                MessageBox.Show("Racks NOT Purged", "Purge Cancelled");
            }
           
        }

        private void DirectoryBox_Click(object sender, EventArgs e)
        {
            using (var diag = new FolderBrowserDialog())
            {
                if(diag.ShowDialog() == DialogResult.OK  && Directory.Exists(diag.SelectedPath))
                {
                    this.UpdatePath(diag.SelectedPath);
                }
            }
        }

        /// <summary>
        /// Update the path as defined by the user
        /// </summary>
        /// <param name="path">
        ///     validated directory path
        /// </param>
        private void UpdatePath(string path)
        {
            //update variable
            this.localDestination = path;
            // update dialog box
            this.directoryBox.Text = path;
            // store path in settings
            Properties.Settings.Default.destination = path;
            Properties.Settings.Default.Save();
        }

        /// <summary>
        /// Disables download and purge buttons.
        /// </summary>
        private void DisableButtons()
        {
            this.downloadButton.Enabled = false;
            this.downloadButton.BackColor = Color.Gray;

            this.purgeButton.Enabled = false;
            this.purgeButton.BackColor = Color.Gray;
        }

        /// <summary>
        /// Enables download and purge buttons.
        /// </summary>
        private void EnableButtons()
        {
            this.downloadButton.Enabled = true;
            this.downloadButton.BackColor = Color.Chartreuse;

            this.purgeButton.Enabled = true;
            this.purgeButton.BackColor = Color.Tomato;
        }

        /// <summary>
        /// Appends a message to the dialog box
        /// </summary>
        /// <param name="message">Message to be appended to dialog box</param>
        private void InformUser(string message)
        {
            this.consoleBox.AppendText(message);
            this.consoleBox.ScrollToCaret();
        }

        /// <summary>
        /// Compresses the directory created during download for archiving.
        /// </summary>
        private void CompressDirectory()
        {
            try
            {
                this.InformUser("Compressing Directory\n");
                string zipPath = this.localDestination + ".zip";
                ZipFile.CreateFromDirectory(this.localDestination, zipPath);
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message, "Could Not compress " + localDestination);
            }
        }

        private void ExitToolStripMenuItem_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void AboutToolStripMenuItem_Click(object sender, EventArgs e)
        {
            //TODO: Create About Form
            AboutBox1 about = new AboutBox1();
            about.Show();
        }

        private void tailNumberTextBox_KeyPress(object sender, KeyPressEventArgs e)
        {
            // only accept digits in textbox
            if (!char.IsControl(e.KeyChar) && !char.IsDigit(e.KeyChar) && e.KeyChar!= '.')
            {
                e.Handled = true;
            }
        }


        private void flightNumberTextBox_KeyPress(object sender, KeyPressEventArgs e)
        {
            // only accept digits in textbox
            if (!char.IsControl(e.KeyChar) && !char.IsDigit(e.KeyChar) && e.KeyChar != '.')
            {
                e.Handled = true;
            }
        }
    }
}