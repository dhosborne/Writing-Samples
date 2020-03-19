namespace FlightBagTool
{
    partial class MainForm
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(MainForm));
            this.callSignComboBox = new System.Windows.Forms.ComboBox();
            this.label1 = new System.Windows.Forms.Label();
            this.rotateBox = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.approachBox = new System.Windows.Forms.TextBox();
            this.label4 = new System.Windows.Forms.Label();
            this.sqawkBox = new System.Windows.Forms.TextBox();
            this.label5 = new System.Windows.Forms.Label();
            this.altitudeBottomBox = new System.Windows.Forms.TextBox();
            this.altitudeTopBox = new System.Windows.Forms.TextBox();
            this.label6 = new System.Windows.Forms.Label();
            this.label7 = new System.Windows.Forms.Label();
            this.liftOffBox = new System.Windows.Forms.TextBox();
            this.addCallsignButton = new System.Windows.Forms.Button();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.gdtBeamButton = new System.Windows.Forms.Button();
            this.lightsButton = new System.Windows.Forms.Button();
            this.speedLeverButton = new System.Windows.Forms.Button();
            this.gdtButton = new System.Windows.Forms.Button();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.segmentsPanel = new System.Windows.Forms.FlowLayoutPanel();
            this.label8 = new System.Windows.Forms.Label();
            this.groupBox3 = new System.Windows.Forms.GroupBox();
            this.menuStrip1 = new System.Windows.Forms.MenuStrip();
            this.fileToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.newMissionToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.exitToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.configureToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.callSignManagerMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.segmentListToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.aboutToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.aboutToolStripMenuItem1 = new System.Windows.Forms.ToolStripMenuItem();
            this.groupBox1.SuspendLayout();
            this.groupBox2.SuspendLayout();
            this.groupBox3.SuspendLayout();
            this.menuStrip1.SuspendLayout();
            this.SuspendLayout();
            // 
            // callSignComboBox
            // 
            this.callSignComboBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 24F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.callSignComboBox.FormattingEnabled = true;
            this.callSignComboBox.Location = new System.Drawing.Point(4, 51);
            this.callSignComboBox.Margin = new System.Windows.Forms.Padding(8, 9, 8, 9);
            this.callSignComboBox.Name = "callSignComboBox";
            this.callSignComboBox.Size = new System.Drawing.Size(190, 45);
            this.callSignComboBox.TabIndex = 0;
            this.callSignComboBox.SelectedValueChanged += new System.EventHandler(this.callSignComboBox_SelectedValueChanged);
            this.callSignComboBox.Click += new System.EventHandler(this.CallSignComboBox_Click);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(8, 22);
            this.label1.Margin = new System.Windows.Forms.Padding(6, 0, 6, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(71, 20);
            this.label1.TabIndex = 1;
            this.label1.Text = "Call Sign";
            // 
            // rotateBox
            // 
            this.rotateBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 24F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.rotateBox.Location = new System.Drawing.Point(18, 56);
            this.rotateBox.Margin = new System.Windows.Forms.Padding(2, 4, 2, 4);
            this.rotateBox.Name = "rotateBox";
            this.rotateBox.Size = new System.Drawing.Size(99, 44);
            this.rotateBox.TabIndex = 2;
            this.rotateBox.Click += new System.EventHandler(this.TextBox_Click);
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(14, 32);
            this.label2.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(58, 20);
            this.label2.TabIndex = 3;
            this.label2.Text = "Rotate";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(262, 32);
            this.label3.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(78, 20);
            this.label3.TabIndex = 5;
            this.label3.Text = "Approach";
            // 
            // approachBox
            // 
            this.approachBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 24F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.approachBox.Location = new System.Drawing.Point(266, 56);
            this.approachBox.Margin = new System.Windows.Forms.Padding(2, 4, 2, 4);
            this.approachBox.Name = "approachBox";
            this.approachBox.Size = new System.Drawing.Size(99, 44);
            this.approachBox.TabIndex = 4;
            this.approachBox.Click += new System.EventHandler(this.TextBox_Click);
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(262, 22);
            this.label4.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(66, 20);
            this.label4.TabIndex = 7;
            this.label4.Text = "Squawk";
            // 
            // sqawkBox
            // 
            this.sqawkBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 24F);
            this.sqawkBox.Location = new System.Drawing.Point(266, 51);
            this.sqawkBox.Margin = new System.Windows.Forms.Padding(2, 4, 2, 4);
            this.sqawkBox.Name = "sqawkBox";
            this.sqawkBox.Size = new System.Drawing.Size(190, 44);
            this.sqawkBox.TabIndex = 6;
            this.sqawkBox.Click += new System.EventHandler(this.TextBox_Click);
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(22, 23);
            this.label5.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(106, 20);
            this.label5.TabIndex = 9;
            this.label5.Text = "Block Altitude";
            // 
            // altitudeBottomBox
            // 
            this.altitudeBottomBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 24F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.altitudeBottomBox.Location = new System.Drawing.Point(19, 47);
            this.altitudeBottomBox.Margin = new System.Windows.Forms.Padding(2, 4, 2, 4);
            this.altitudeBottomBox.Name = "altitudeBottomBox";
            this.altitudeBottomBox.Size = new System.Drawing.Size(102, 44);
            this.altitudeBottomBox.TabIndex = 8;
            this.altitudeBottomBox.Click += new System.EventHandler(this.TextBox_Click);
            // 
            // altitudeTopBox
            // 
            this.altitudeTopBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 24F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.altitudeTopBox.Location = new System.Drawing.Point(168, 47);
            this.altitudeTopBox.Margin = new System.Windows.Forms.Padding(2, 4, 2, 4);
            this.altitudeTopBox.Name = "altitudeTopBox";
            this.altitudeTopBox.Size = new System.Drawing.Size(99, 44);
            this.altitudeTopBox.TabIndex = 10;
            this.altitudeTopBox.Click += new System.EventHandler(this.TextBox_Click);
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Font = new System.Drawing.Font("Microsoft Sans Serif", 24F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label6.Location = new System.Drawing.Point(136, 50);
            this.label6.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(27, 37);
            this.label6.TabIndex = 11;
            this.label6.Text = "-";
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Location = new System.Drawing.Point(142, 32);
            this.label7.Margin = new System.Windows.Forms.Padding(2, 0, 2, 0);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(58, 20);
            this.label7.TabIndex = 13;
            this.label7.Text = "Lift-Off";
            // 
            // liftOffBox
            // 
            this.liftOffBox.Font = new System.Drawing.Font("Microsoft Sans Serif", 24F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.liftOffBox.Location = new System.Drawing.Point(142, 56);
            this.liftOffBox.Margin = new System.Windows.Forms.Padding(2, 4, 2, 4);
            this.liftOffBox.Name = "liftOffBox";
            this.liftOffBox.Size = new System.Drawing.Size(99, 44);
            this.liftOffBox.TabIndex = 12;
            this.liftOffBox.Click += new System.EventHandler(this.TextBox_Click);
            // 
            // addCallsignButton
            // 
            this.addCallsignButton.Location = new System.Drawing.Point(193, 51);
            this.addCallsignButton.Margin = new System.Windows.Forms.Padding(2, 4, 2, 4);
            this.addCallsignButton.Name = "addCallsignButton";
            this.addCallsignButton.Size = new System.Drawing.Size(47, 45);
            this.addCallsignButton.TabIndex = 14;
            this.addCallsignButton.Text = "+";
            this.addCallsignButton.UseVisualStyleBackColor = true;
            this.addCallsignButton.Click += new System.EventHandler(this.AddCallsignButton_Click);
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.gdtBeamButton);
            this.groupBox1.Controls.Add(this.lightsButton);
            this.groupBox1.Controls.Add(this.speedLeverButton);
            this.groupBox1.Controls.Add(this.gdtButton);
            this.groupBox1.Controls.Add(this.callSignComboBox);
            this.groupBox1.Controls.Add(this.addCallsignButton);
            this.groupBox1.Controls.Add(this.label1);
            this.groupBox1.Controls.Add(this.sqawkBox);
            this.groupBox1.Controls.Add(this.label4);
            this.groupBox1.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.groupBox1.Location = new System.Drawing.Point(15, 45);
            this.groupBox1.Margin = new System.Windows.Forms.Padding(2, 4, 2, 4);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Padding = new System.Windows.Forms.Padding(2, 4, 2, 4);
            this.groupBox1.Size = new System.Drawing.Size(483, 187);
            this.groupBox1.TabIndex = 15;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Identification";
            // 
            // gdtBeamButton
            // 
            this.gdtBeamButton.BackColor = System.Drawing.Color.Chartreuse;
            this.gdtBeamButton.Location = new System.Drawing.Point(363, 109);
            this.gdtBeamButton.Name = "gdtBeamButton";
            this.gdtBeamButton.Size = new System.Drawing.Size(113, 71);
            this.gdtBeamButton.TabIndex = 18;
            this.gdtBeamButton.UseVisualStyleBackColor = false;
            this.gdtBeamButton.Click += new System.EventHandler(this.gdtBeamButton_Click);
            // 
            // lightsButton
            // 
            this.lightsButton.BackColor = System.Drawing.Color.Chartreuse;
            this.lightsButton.Location = new System.Drawing.Point(244, 109);
            this.lightsButton.Name = "lightsButton";
            this.lightsButton.Size = new System.Drawing.Size(113, 71);
            this.lightsButton.TabIndex = 17;
            this.lightsButton.UseVisualStyleBackColor = false;
            this.lightsButton.Click += new System.EventHandler(this.LightsButton_Click);
            // 
            // speedLeverButton
            // 
            this.speedLeverButton.BackColor = System.Drawing.Color.Chartreuse;
            this.speedLeverButton.Location = new System.Drawing.Point(125, 109);
            this.speedLeverButton.Name = "speedLeverButton";
            this.speedLeverButton.Size = new System.Drawing.Size(113, 71);
            this.speedLeverButton.TabIndex = 16;
            this.speedLeverButton.UseVisualStyleBackColor = false;
            this.speedLeverButton.Click += new System.EventHandler(this.GearButton_Click);
            // 
            // gdtButton
            // 
            this.gdtButton.BackColor = System.Drawing.Color.Chartreuse;
            this.gdtButton.Location = new System.Drawing.Point(6, 109);
            this.gdtButton.Name = "gdtButton";
            this.gdtButton.Size = new System.Drawing.Size(113, 71);
            this.gdtButton.TabIndex = 15;
            this.gdtButton.UseVisualStyleBackColor = false;
            this.gdtButton.Click += new System.EventHandler(this.GdtButton_Click);
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.segmentsPanel);
            this.groupBox2.Controls.Add(this.label8);
            this.groupBox2.Controls.Add(this.label5);
            this.groupBox2.Controls.Add(this.altitudeBottomBox);
            this.groupBox2.Controls.Add(this.altitudeTopBox);
            this.groupBox2.Controls.Add(this.label6);
            this.groupBox2.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.groupBox2.Location = new System.Drawing.Point(13, 250);
            this.groupBox2.Margin = new System.Windows.Forms.Padding(2, 4, 2, 4);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Padding = new System.Windows.Forms.Padding(2, 4, 2, 4);
            this.groupBox2.Size = new System.Drawing.Size(485, 363);
            this.groupBox2.TabIndex = 16;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "Clearance";
            // 
            // segmentsPanel
            // 
            this.segmentsPanel.Location = new System.Drawing.Point(6, 132);
            this.segmentsPanel.Name = "segmentsPanel";
            this.segmentsPanel.Size = new System.Drawing.Size(474, 224);
            this.segmentsPanel.TabIndex = 13;
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Location = new System.Drawing.Point(10, 109);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(82, 20);
            this.label8.TabIndex = 12;
            this.label8.Text = "Segments";
            // 
            // groupBox3
            // 
            this.groupBox3.Controls.Add(this.label2);
            this.groupBox3.Controls.Add(this.rotateBox);
            this.groupBox3.Controls.Add(this.approachBox);
            this.groupBox3.Controls.Add(this.label7);
            this.groupBox3.Controls.Add(this.label3);
            this.groupBox3.Controls.Add(this.liftOffBox);
            this.groupBox3.Font = new System.Drawing.Font("Microsoft Sans Serif", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.groupBox3.Location = new System.Drawing.Point(13, 620);
            this.groupBox3.Name = "groupBox3";
            this.groupBox3.Size = new System.Drawing.Size(483, 131);
            this.groupBox3.TabIndex = 17;
            this.groupBox3.TabStop = false;
            this.groupBox3.Text = "Speeds";
            // 
            // menuStrip1
            // 
            this.menuStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.fileToolStripMenuItem,
            this.configureToolStripMenuItem,
            this.aboutToolStripMenuItem});
            this.menuStrip1.Location = new System.Drawing.Point(0, 0);
            this.menuStrip1.Name = "menuStrip1";
            this.menuStrip1.Size = new System.Drawing.Size(517, 24);
            this.menuStrip1.TabIndex = 18;
            this.menuStrip1.Text = "menuStrip1";
            // 
            // fileToolStripMenuItem
            // 
            this.fileToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.newMissionToolStripMenuItem,
            this.exitToolStripMenuItem});
            this.fileToolStripMenuItem.Name = "fileToolStripMenuItem";
            this.fileToolStripMenuItem.Size = new System.Drawing.Size(37, 20);
            this.fileToolStripMenuItem.Text = "File";
            // 
            // newMissionToolStripMenuItem
            // 
            this.newMissionToolStripMenuItem.Name = "newMissionToolStripMenuItem";
            this.newMissionToolStripMenuItem.Size = new System.Drawing.Size(142, 22);
            this.newMissionToolStripMenuItem.Text = "New Mission";
            this.newMissionToolStripMenuItem.Click += new System.EventHandler(this.newMissionToolStripMenuItem_Click);
            // 
            // exitToolStripMenuItem
            // 
            this.exitToolStripMenuItem.Name = "exitToolStripMenuItem";
            this.exitToolStripMenuItem.Size = new System.Drawing.Size(142, 22);
            this.exitToolStripMenuItem.Text = "Exit";
            this.exitToolStripMenuItem.Click += new System.EventHandler(this.ExitToolStripMenuItem_Click);
            // 
            // configureToolStripMenuItem
            // 
            this.configureToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.callSignManagerMenuItem,
            this.segmentListToolStripMenuItem});
            this.configureToolStripMenuItem.Name = "configureToolStripMenuItem";
            this.configureToolStripMenuItem.Size = new System.Drawing.Size(72, 20);
            this.configureToolStripMenuItem.Text = "Configure";
            // 
            // callSignManagerMenuItem
            // 
            this.callSignManagerMenuItem.Name = "callSignManagerMenuItem";
            this.callSignManagerMenuItem.Size = new System.Drawing.Size(142, 22);
            this.callSignManagerMenuItem.Text = "Call Signs";
            this.callSignManagerMenuItem.Click += new System.EventHandler(this.CallSignManagerMenuItem_Click);
            // 
            // segmentListToolStripMenuItem
            // 
            this.segmentListToolStripMenuItem.Name = "segmentListToolStripMenuItem";
            this.segmentListToolStripMenuItem.Size = new System.Drawing.Size(142, 22);
            this.segmentListToolStripMenuItem.Text = "Segment List";
            this.segmentListToolStripMenuItem.Click += new System.EventHandler(this.SegmentListToolStripMenuItem_Click);
            // 
            // aboutToolStripMenuItem
            // 
            this.aboutToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.aboutToolStripMenuItem1});
            this.aboutToolStripMenuItem.Name = "aboutToolStripMenuItem";
            this.aboutToolStripMenuItem.Size = new System.Drawing.Size(52, 20);
            this.aboutToolStripMenuItem.Text = "About";
            // 
            // aboutToolStripMenuItem1
            // 
            this.aboutToolStripMenuItem1.Name = "aboutToolStripMenuItem1";
            this.aboutToolStripMenuItem1.Size = new System.Drawing.Size(107, 22);
            this.aboutToolStripMenuItem1.Text = "About";
            this.aboutToolStripMenuItem1.Click += new System.EventHandler(this.AboutToolStripMenuItem1_Click);
            // 
            // MainForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(19F, 37F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(517, 794);
            this.Controls.Add(this.groupBox3);
            this.Controls.Add(this.groupBox2);
            this.Controls.Add(this.groupBox1);
            this.Controls.Add(this.menuStrip1);
            this.Font = new System.Drawing.Font("Microsoft Sans Serif", 24F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Location = new System.Drawing.Point(10, 10);
            this.MainMenuStrip = this.menuStrip1;
            this.Margin = new System.Windows.Forms.Padding(8, 9, 8, 9);
            this.Name = "MainForm";
            this.Text = "Flight Bag Tool";
            this.Load += new System.EventHandler(this.Form1_Load);
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.groupBox2.ResumeLayout(false);
            this.groupBox2.PerformLayout();
            this.groupBox3.ResumeLayout(false);
            this.groupBox3.PerformLayout();
            this.menuStrip1.ResumeLayout(false);
            this.menuStrip1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.ComboBox callSignComboBox;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox rotateBox;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox approachBox;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.TextBox sqawkBox;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.TextBox altitudeBottomBox;
        private System.Windows.Forms.TextBox altitudeTopBox;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.TextBox liftOffBox;
        private System.Windows.Forms.Button addCallsignButton;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.GroupBox groupBox2;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.GroupBox groupBox3;
        private System.Windows.Forms.Button gdtButton;
        private System.Windows.Forms.Button speedLeverButton;
        private System.Windows.Forms.Button lightsButton;
        private System.Windows.Forms.MenuStrip menuStrip1;
        private System.Windows.Forms.ToolStripMenuItem fileToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem exitToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem configureToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem callSignManagerMenuItem;
        private System.Windows.Forms.ToolStripMenuItem aboutToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem aboutToolStripMenuItem1;
        private System.Windows.Forms.FlowLayoutPanel segmentsPanel;
        private System.Windows.Forms.ToolStripMenuItem segmentListToolStripMenuItem;
        private System.Windows.Forms.Button gdtBeamButton;
        private System.Windows.Forms.ToolStripMenuItem newMissionToolStripMenuItem;
    }
}

