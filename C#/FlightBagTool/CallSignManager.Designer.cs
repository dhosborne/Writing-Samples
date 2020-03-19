namespace FlightBagTool
{
    partial class CallSignManager
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(CallSignManager));
            this.removeButton = new System.Windows.Forms.Button();
            this.callSignBox = new System.Windows.Forms.ComboBox();
            this.cancelButton = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // removeButton
            // 
            this.removeButton.BackColor = System.Drawing.Color.Gray;
            this.removeButton.Enabled = false;
            this.removeButton.Location = new System.Drawing.Point(263, 10);
            this.removeButton.Name = "removeButton";
            this.removeButton.Size = new System.Drawing.Size(174, 47);
            this.removeButton.TabIndex = 1;
            this.removeButton.Text = "Remove";
            this.removeButton.UseVisualStyleBackColor = false;
            this.removeButton.Click += new System.EventHandler(this.removeButton_Click);
            // 
            // callSignBox
            // 
            this.callSignBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 24F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.callSignBox.FormattingEnabled = true;
            this.callSignBox.Location = new System.Drawing.Point(12, 12);
            this.callSignBox.Name = "callSignBox";
            this.callSignBox.Size = new System.Drawing.Size(245, 45);
            this.callSignBox.TabIndex = 2;
            this.callSignBox.SelectedIndexChanged += new System.EventHandler(this.callSignBox_SelectedIndexChanged);
            this.callSignBox.Click += new System.EventHandler(this.callSignBox_Click);
            // 
            // cancelButton
            // 
            this.cancelButton.BackColor = System.Drawing.Color.Tomato;
            this.cancelButton.Location = new System.Drawing.Point(443, 10);
            this.cancelButton.Name = "cancelButton";
            this.cancelButton.Size = new System.Drawing.Size(174, 47);
            this.cancelButton.TabIndex = 3;
            this.cancelButton.Text = "Cancel";
            this.cancelButton.UseVisualStyleBackColor = false;
            this.cancelButton.Click += new System.EventHandler(this.cancelButton_Click);
            // 
            // CallSignManager
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(720, 78);
            this.Controls.Add(this.cancelButton);
            this.Controls.Add(this.callSignBox);
            this.Controls.Add(this.removeButton);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "CallSignManager";
            this.Text = "CallSignManager";
            this.ResumeLayout(false);

        }

        #endregion
        private System.Windows.Forms.Button removeButton;
        private System.Windows.Forms.ComboBox callSignBox;
        private System.Windows.Forms.Button cancelButton;
    }
}