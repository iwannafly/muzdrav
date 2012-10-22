package ru.nkz.ivcgzo.Infomat;

public class FrameManager {
    private MainFrame frmMain;
    
    public void startApplication() {
        frmMain = new MainFrame();
        frmMain.pack();
        frmMain.setVisible(true);
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        FrameManager frmManager = new FrameManager();
        frmManager.startApplication();

    }

}
