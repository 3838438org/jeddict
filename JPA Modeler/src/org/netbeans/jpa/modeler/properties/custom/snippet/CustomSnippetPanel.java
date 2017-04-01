/**
 * Copyright [2016] Gaurav Gupta
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.netbeans.jpa.modeler.properties.custom.snippet;

import javax.swing.JOptionPane;
import static org.apache.commons.lang.StringUtils.EMPTY;
import org.netbeans.jpa.modeler.internal.jpqleditor.ModelerPanel;
import org.netbeans.jpa.modeler.spec.extend.AttributeSnippetLocationType;
import org.netbeans.jpa.modeler.spec.extend.ClassSnippet;
import org.netbeans.jpa.modeler.spec.extend.ClassSnippetLocationType;
import org.netbeans.jpa.modeler.spec.extend.Snippet;
import org.netbeans.jpa.modeler.spec.extend.SnippetLocation;
import org.netbeans.modeler.core.ModelerFile;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.entity.ComboBoxValue;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.entity.Entity;
import org.netbeans.modeler.properties.entity.custom.editor.combobox.client.entity.RowValue;
import org.netbeans.modeler.properties.EntityComponent;
import org.openide.util.Exceptions;

public class CustomSnippetPanel<T extends Snippet> extends EntityComponent<Snippet> implements ModelerPanel {

    private Snippet snippet;
    private final ModelerFile modelerFile;
    private final Class<? extends Snippet> snippetType;

    public CustomSnippetPanel(ModelerFile modelerFile, Class<? extends Snippet> snippetType) {
        this.modelerFile = modelerFile;
        this.snippetType=snippetType;
    }

    @Override
    public void postConstruct() {
        initComponents();
        snippetLocationTypeInit();
    }

    @Override
    public void init() {
        scopeComboBox.setSelectedIndex(0);
    }

    @Override
    public void createEntity(Class<? extends Entity> entityWrapperType) {
        this.setTitle("Add new Snippet");
        if (entityWrapperType == RowValue.class) {
            this.setEntity(new RowValue(new Object[4]));
        }
        snippet = null;
        customCodeEditorPane.setText(EMPTY);
    }

    @Override
    public void updateEntity(Entity<Snippet> entityValue) {
        this.setTitle("Update Snippet");
        if (entityValue.getClass() == RowValue.class) {
            this.setEntity(entityValue);
            Object[] row = ((RowValue) entityValue).getRow();
            snippet = (Snippet) row[0];
            customCodeEditorPane.setText(snippet.getValue());
            setSnippetLocationType(snippet.getLocationType());
        }

    }

    private void snippetLocationTypeInit() {
        scopeComboBox.removeAllItems();
        for (SnippetLocation locationType : (snippetType==ClassSnippet.class?ClassSnippetLocationType.values():AttributeSnippetLocationType.values())) {
            scopeComboBox.addItem(new ComboBoxValue(locationType, locationType.getTitle()));
        }
    }

    private void setSnippetLocationType(SnippetLocation locationType) {
        if (locationType == null) {
            scopeComboBox.setSelectedIndex(0);
        } else {
            for (int i = 0; i < scopeComboBox.getItemCount(); i++) {
                if (((ComboBoxValue<SnippetLocation>) scopeComboBox.getItemAt(i)).getValue() == locationType) {
                    scopeComboBox.setSelectedIndex(i);
                }
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rootLayeredPane = new javax.swing.JLayeredPane();
        actionLayeredPane = new javax.swing.JLayeredPane();
        saveButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        customCodeScrollPane = new javax.swing.JScrollPane();
        customCodeEditorPane = new javax.swing.JEditorPane();
        scopeComboBox = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        org.openide.awt.Mnemonics.setLocalizedText(saveButton, org.openide.util.NbBundle.getMessage(CustomSnippetPanel.class, "CustomSnippetPanel.saveButton.text")); // NOI18N
        saveButton.setToolTipText(org.openide.util.NbBundle.getMessage(CustomSnippetPanel.class, "CustomSnippetPanel.saveButton.toolTipText")); // NOI18N
        saveButton.setSelected(true);
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(cancelButton, org.openide.util.NbBundle.getMessage(CustomSnippetPanel.class, "CustomSnippetPanel.cancelButton.text")); // NOI18N
        cancelButton.setToolTipText(org.openide.util.NbBundle.getMessage(CustomSnippetPanel.class, "CustomSnippetPanel.cancelButton.toolTipText")); // NOI18N
        cancelButton.setPreferredSize(new java.awt.Dimension(60, 23));
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        actionLayeredPane.setLayer(saveButton, javax.swing.JLayeredPane.DEFAULT_LAYER);
        actionLayeredPane.setLayer(cancelButton, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout actionLayeredPaneLayout = new javax.swing.GroupLayout(actionLayeredPane);
        actionLayeredPane.setLayout(actionLayeredPaneLayout);
        actionLayeredPaneLayout.setHorizontalGroup(
            actionLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(actionLayeredPaneLayout.createSequentialGroup()
                .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        actionLayeredPaneLayout.setVerticalGroup(
            actionLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(actionLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(saveButton)
                .addComponent(cancelButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        customCodeScrollPane.setPreferredSize(new java.awt.Dimension(400, 100));

        customCodeEditorPane.setContentType("text/x-java"); // NOI18N
        customCodeEditorPane.setAutoscrolls(false);
        customCodeEditorPane.setPreferredSize(new java.awt.Dimension(210, 23));
        customCodeScrollPane.setViewportView(customCodeEditorPane);

        scopeComboBox.setToolTipText(org.openide.util.NbBundle.getMessage(CustomSnippetPanel.class, "CustomSnippetPanel.scopeComboBox.toolTipText")); // NOI18N

        rootLayeredPane.setLayer(actionLayeredPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        rootLayeredPane.setLayer(customCodeScrollPane, javax.swing.JLayeredPane.DEFAULT_LAYER);
        rootLayeredPane.setLayer(scopeComboBox, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout rootLayeredPaneLayout = new javax.swing.GroupLayout(rootLayeredPane);
        rootLayeredPane.setLayout(rootLayeredPaneLayout);
        rootLayeredPaneLayout.setHorizontalGroup(
            rootLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rootLayeredPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(rootLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(customCodeScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE)
                    .addGroup(rootLayeredPaneLayout.createSequentialGroup()
                        .addComponent(scopeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(actionLayeredPane, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        rootLayeredPaneLayout.setVerticalGroup(
            rootLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rootLayeredPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(customCodeScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(rootLayeredPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scopeComboBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(actionLayeredPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rootLayeredPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rootLayeredPane)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private boolean validateField() {
        if (this.customCodeEditorPane.getText().trim().length() <= 0 /*|| Pattern.compile("[^\\w-]").matcher(this.id_TextField.getText().trim()).find()*/) {
            JOptionPane.showMessageDialog(this, "Snippet can't be empty", "Invalid Value", javax.swing.JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        if (!validateField()) {
            return;
        }
        if (this.getEntity().getClass() == RowValue.class) {
            Object[] row = ((RowValue) this.getEntity()).getRow();
            if (row[0] == null) {
                snippet = newSnippet();
            } else {
                snippet = (Snippet) row[0];
            }
        }

        snippet.setValue(customCodeEditorPane.getText());
        snippet.setLocationType(((ComboBoxValue<SnippetLocation>) scopeComboBox.getSelectedItem()).getValue());

        if (this.getEntity().getClass() == RowValue.class) {
            Object[] row = ((RowValue) this.getEntity()).getRow();
            row[0] = snippet;
            row[1] = snippet.isEnable();
            row[2] = snippet.getValue();
            row[3] = snippet.getLocationType().getTitle();
        }
        saveActionPerformed(evt);
    }//GEN-LAST:event_saveButtonActionPerformed

    
    private Snippet newSnippet()
    {
        try {
            return snippetType.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            Exceptions.printStackTrace(ex);
            throw new IllegalStateException(ex);
        }
        
    }
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        cancelActionPerformed(evt);
    }//GEN-LAST:event_cancelButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane actionLayeredPane;
    private javax.swing.JButton cancelButton;
    private javax.swing.JEditorPane customCodeEditorPane;
    private javax.swing.JScrollPane customCodeScrollPane;
    private javax.swing.JLayeredPane rootLayeredPane;
    private javax.swing.JButton saveButton;
    private javax.swing.JComboBox scopeComboBox;
    // End of variables declaration//GEN-END:variables

    @Override
    public ModelerFile getModelerFile() {
        return modelerFile;
    }
}
