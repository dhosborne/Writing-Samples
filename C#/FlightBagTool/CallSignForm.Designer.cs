namespace FlightBagTool
{
    partial class CallSignForm
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
            this.prefixComboBox = new System.Windows.Forms.ComboBox();
            this.SuffixBox = new System.Windows.Forms.TextBox();
            this.PrefixLabel = new System.Windows.Forms.Label();
            this.SuffixLabel = new System.Windows.Forms.Label();
            this.okButton = new System.Windows.Forms.Button();
            this.cancelButton = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // prefixComboBox
            // 
            this.prefixComboBox.FormattingEnabled = true;
            this.prefixComboBox.Location = new System.Drawing.Point(66, 30);
            this.prefixComboBox.Name = "prefixComboBox";
            this.prefixComboBox.Size = new System.Drawing.Size(121, 28);
            this.prefixComboBox.TabIndex = 0;
            // 
            // SuffixBox
            // 
            this.SuffixBox.Location = new System.Drawing.Point(66, 93);
            this.SuffixBox.Name = "SuffixBox";
            this.SuffixBox.Size = new System.Drawing.Size(121, 26);
            this.SuffixBox.TabIndex = 1;
            this.SuffixBox.Click += new System.EventHandler(this.SuffixBox_Click);
            // 
            // PrefixLabel
            // 
            this.PrefixLabel.AutoSize = true;
            this.PrefixLabel.Location = new System.Drawing.Point(12, 30);
            this.PrefixLabel.Name = "PrefixLabel";
            this.PrefixLabel.Size = new System.Drawing.Size(48, 20);
            this.PrefixLabel.TabIndex = 2;
            this.PrefixLabel.Text = "Prefix";
            // 
            // SuffixLabel
            // 
            this.SuffixLabel.AutoSize = true;
            this.SuffixLabel.Location = new System.Drawing.Point(12, 96);
            this.SuffixLabel.Name = "SuffixLabel";
            this.SuffixLabel.Size = new System.Drawing.Size(49, 20);
            this.SuffixLabel.TabIndex = 3;
            this.SuffixLabel.Text = "Suffix";
            // 
            // okButton
            // 
            this.okButton.BackColor = System.Drawing.Color.Chartreuse;
            this.okButton.Location = new System.Drawing.Point(111, 149);
            this.okButton.Name = "okButton";
            this.okButton.Size = new System.Drawing.Size(75, 33);
            this.okButton.TabIndex = 4;
            this.okButton.Text = "OK";
            this.okButton.UseVisualStyleBackColor = false;
            this.okButton.Click += new System.EventHandler(this.okButton_Click);
            // 
            // cancelButton
            // 
            this.cancelButton.BackColor = System.Drawing.Color.Tomato;
            this.cancelButton.Location = new System.Drawing.Point(30, 149);
            this.cancelButton.Name = "cancelButton";
            this.cancelButton.Size = new System.Drawing.Size(75, 33);
            this.cancelButton.TabIndex = 5;
            this.cancelButton.Text = "Cancel";
            this.cancelButton.UseVisualStyleBackColor = false;
            this.cancelButton.Click += new System.EventHandler(this.cancelButton_Click);
            // 
            // CallSignForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 20F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(260, 194);
            this.Controls.Add(this.cancelButton);
            this.Controls.Add(this.okButton);
            this.Controls.Add(this.SuffixLabel);
            this.Controls.Add(this.PrefixLabel);
            this.Controls.Add(this.SuffixBox);
            this.Controls.Add(this.prefixComboBox);
            this.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Margin = new System.Windows.Forms.Padding(4, 5, 4, 5);
            this.Name = "CallSignForm";
            this.Text = "CallSignForm";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.ComboBox prefixComboBox;
        private System.Windows.Forms.TextBox SuffixBox;
        private System.Windows.Forms.Label PrefixLabel;
        private System.Windows.Forms.Label SuffixLabel;
        private System.Windows.Forms.Button okButton;
        private System.Windows.Forms.Button cancelButton;
    }
}