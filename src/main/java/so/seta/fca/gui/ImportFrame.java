/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package so.seta.fca.gui;


import java.awt.Cursor;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.JFileChooser;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;
import so.seta.fca.entity.Result;
import so.seta.fca.util.ExcelImport;
import so.seta.fca.util.LoggerNew;
import static so.seta.fca.util.Utility.estraiEccezione;

/**
 *
 * @author Roberto Federico
 */
public class ImportFrame extends javax.swing.JFrame {
    public static final ResourceBundle conf = ResourceBundle.getBundle("conf.conf");   
    public static final String PATH = conf.getString("path.log");
    public static LoggerNew LOGGER = new LoggerNew("FCA_TRACK",PATH);
    private Cursor waitCursor = new Cursor(Cursor.HAND_CURSOR);
    private Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
    private String defaultButtonText = "Show Wait Cursor";
    private List<String> retPratiche;
    // a default constructor
//    filechooser()
//    {
//    } 
    /**
     * Creates new form ImportFrame
     * @param frame
     */
    public ImportFrame(MainFrame frame) {
     
         
        initComponents();
        jFileChooser1.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel *.xlsx", "xlsx");
        jFileChooser1.addChoosableFileFilter(filter);
                     
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaEsito = new javax.swing.JTextArea();
        progressBar = new javax.swing.JProgressBar();

        jFileChooser1.setFileFilter(null);
        jFileChooser1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFileChooser1ActionPerformed(evt);
            }
        });
        jFileChooser1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jFileChooser1PropertyChange(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Caricamento File");

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search.png"))); // NOI18N
        jButton1.setText("Ricerca File");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextAreaEsito.setColumns(20);
        jTextAreaEsito.setRows(5);
        jScrollPane1.setViewportView(jTextAreaEsito);

        progressBar.setBackground(new java.awt.Color(255, 0, 102));
        progressBar.setStringPainted(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(173, 173, 173)
                        .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 730, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1)
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jFileChooser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileChooser1ActionPerformed
        // TODO add your handling code here:
//    if (evt.getActionCommand().equals(javax.swing.JFileChooser.APPROVE_SELECTION)) {
//        jTextAreaEsito.append("\n file selezionato");
//    } else if (evt.getActionCommand().equals(javax.swing.JFileChooser.CANCEL_SELECTION)) {
//        jTextAreaEsito.append("\n cancel selection");
//    }
    }//GEN-LAST:event_jFileChooser1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        int returnVal = jFileChooser1.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                 File file = jFileChooser1.getSelectedFile();
                 //ExcelImport.ReadFromExcel(file.getAbsolutePath());
                 this.setCursor(waitCursor);
                 new FileUploadTask(file).execute();
            
                 this.setCursor(defaultCursor);
                }
            catch (Exception ex) {
                LOGGER.log.severe(estraiEccezione(ex));
//            } catch (InterruptedException ex) {
//                 LOGGER.log.severe(estraiEccezione(ex));
            }
            
        } else {
            System.out.println("File access cancelled by user.");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jFileChooser1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jFileChooser1PropertyChange
        // TOJDO add your handling code here:
        if (JFileChooser.SELECTED_FILE_CHANGED_PROPERTY
                .equals(evt.getPropertyName())) {
            JFileChooser chooser = (JFileChooser)evt.getSource();
            File curFile = chooser.getSelectedFile();
            if (curFile != null)
                jTextAreaEsito.setText(curFile + " selezionato\n");
        } else if (JFileChooser.SELECTED_FILES_CHANGED_PROPERTY.equals(
                evt.getPropertyName())) {
            JFileChooser chooser = (JFileChooser)evt.getSource();
            File[] files = chooser.getSelectedFiles();
        }
    
    }//GEN-LAST:event_jFileChooser1PropertyChange
    
     private class FileUploadTask extends SwingWorker<Void, Integer> {
        private final File file;
        private final long start = System.currentTimeMillis();
        private String esito = "";
        public FileUploadTask(File file) {
           this.file = file;
           jTextAreaEsito.append("Inizio Importazione Pratiche\n");
        }

        @Override
        protected Void doInBackground() throws Exception {
            uploadFile(file);
            return null;
        }

        private void uploadFile(File file) throws IOException {
            
            
            try (FileInputStream in = new FileInputStream(file)) {

                byte[] buffer = new byte[4096];
                long totalBytes = file.length();
                long bytesUploaded = 0;
                int bytesRead;

                while ((bytesRead = in.read(buffer)) != -1) {
//                    out.write(buffer, 0, bytesRead);
                    bytesUploaded += bytesRead;
                    int progress = (int) ((bytesUploaded * 100) / totalBytes);
                    publish(progress);
                }
                    Result rit = ExcelImport.ReadAndInsertFromExcel(file.getAbsolutePath());
                    if (rit.getEsito()) {
                        esito = rit.getMessaggio();
                    }
                    else {
                        esito = "Nessuna pratica caricata"; 
                    }
                
            }


        }

        @Override
        protected void process(java.util.List<Integer> chunks) {                                            
            int latestProgress = chunks.get(chunks.size() - 1);
            progressBar.setValue(latestProgress);
            progressBar.setIndeterminate(true);
            progressBar.setString("Caricamento...");
        }

        @Override
        protected void done() {
            
           //long end = System.currentTimeMillis();
           jTextAreaEsito.append("Importazione pratiche eseguita\n");
           jTextAreaEsito.append(this.esito);
           jTextAreaEsito.append("\n");
           //jTextAreaEsito.append(String.format("Lettura file in %d ms\n", (end - start)));
           progressBar.setStringPainted(false);
           progressBar.setIndeterminate(false);
           progressBar.setString(null);
           // JOptionPane.showMessageDialog(null,
           //         "Caricamento pratiche completato!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTextArea jTextAreaEsito;
    private javax.swing.JProgressBar progressBar;
    // End of variables declaration//GEN-END:variables
}
