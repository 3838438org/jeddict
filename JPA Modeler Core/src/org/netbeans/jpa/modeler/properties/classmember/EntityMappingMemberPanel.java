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
package org.netbeans.jpa.modeler.properties.classmember;

import org.netbeans.jpa.modeler.navigator.nodes.CheckableAttributeNode;
import javax.swing.SwingUtilities;
import org.netbeans.jpa.modeler.navigator.nodes.TreeChildNode;
import org.netbeans.jpa.modeler.navigator.nodes.TreeNode;
import org.netbeans.jpa.modeler.navigator.nodes.TreeParentNode;
import org.netbeans.jpa.modeler.properties.rootmember.nodes.EntityManagerChildFactory;
import org.netbeans.jpa.modeler.properties.rootmember.nodes.RMLeafNode;
import org.netbeans.jpa.modeler.properties.rootmember.nodes.RMRootNode;
import org.netbeans.jpa.modeler.spec.EntityMappings;
import org.netbeans.jpa.modeler.spec.extend.JavaClass;
import org.netbeans.jpa.modeler.specification.model.scene.JPAModelerScene;
import org.netbeans.modeler.properties.embedded.GenericEmbeddedEditor;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.view.OutlineView;

public class EntityMappingMemberPanel extends GenericEmbeddedEditor<EntityMappings> implements ExplorerManager.Provider {

    private ExplorerManager manager;
    private final String title;

    private EntityMappings entityMappings;
    private JPAModelerScene scene;
    private RMRootNode node;

    public EntityMappingMemberPanel(String title, JPAModelerScene scene) {
        this.scene = scene;
        this.title=title;
    }

    @Override
    public void init() {
        manager = new ExplorerManager();
        initComponents();
    }

    @Override
    public void setValue(EntityMappings entityMappings) {
        this.entityMappings = entityMappings;
        SwingUtilities.invokeLater(() -> {
            node = new RMRootNode(scene, entityMappings, new EntityManagerChildFactory(), new CheckableAttributeNode());
            manager.setRootContext(node);
            node.init();
        });
    }

    @Override
    public EntityMappings getValue() {
        loadClassNode(node);
        return entityMappings;
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
        outlineView = new OutlineView(getTitle());

        rootLayeredPane.setLayout(new java.awt.GridLayout(1, 0));

        outlineView.setToolTipText(org.openide.util.NbBundle.getMessage(EntityMappingMemberPanel.class, "EntityMappingMemberPanel.outlineView.toolTipText")); // NOI18N
        rootLayeredPane.add(outlineView);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rootLayeredPane, javax.swing.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rootLayeredPane, javax.swing.GroupLayout.DEFAULT_SIZE, 536, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void loadClassNode(TreeNode parentNode) {
        if (parentNode instanceof TreeParentNode) {
            for (TreeNode childNode : ((TreeParentNode<EntityMappings>) parentNode).getChildList()) {
                if (childNode instanceof TreeChildNode && childNode.getCheckableNode() != null) {
                    JavaClass javaClass = ((JavaClass) (((RMLeafNode) childNode).getLeafWidget().getBaseElementSpec()));
                    javaClass.setGenerateSourceCode(childNode.getCheckableNode().isSelected());
                }
            }
        }

    }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane outlineView;
    private javax.swing.JLayeredPane rootLayeredPane;
    // End of variables declaration//GEN-END:variables

    @Override
    public ExplorerManager getExplorerManager() {
        return manager;
    }

    /**
     * @param scene the scene to set
     */
    public void setSceneWidget(JPAModelerScene scene) {
        this.scene = scene;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

}
