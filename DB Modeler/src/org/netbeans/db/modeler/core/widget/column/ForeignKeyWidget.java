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
package org.netbeans.db.modeler.core.widget.column;

import java.awt.Image;
import org.netbeans.db.modeler.spec.DBColumn;
import org.netbeans.db.modeler.specification.model.scene.DBModelerScene;
import org.netbeans.db.modeler.specification.model.util.DBModelerUtil;
import static org.netbeans.db.modeler.specification.model.util.DBModelerUtil.FOREIGNKEY;
import static org.netbeans.db.modeler.specification.model.util.DBModelerUtil.FOREIGNKEY_ICON_PATH;
import org.netbeans.modeler.widget.node.IPNodeWidget;
import org.netbeans.modeler.widget.pin.info.PinWidgetInfo;

/**
 *
 * @author Gaurav Gupta
 */
public abstract class ForeignKeyWidget<E extends DBColumn> extends ColumnWidget<E> implements IReferenceColumnWidget<E>{

    public ForeignKeyWidget(DBModelerScene scene, IPNodeWidget nodeWidget, PinWidgetInfo pinWidgetInfo) {
        super(scene, nodeWidget, pinWidgetInfo);
        
    }
    
    @Override
    public void init() {
        super.init();
        if (((DBColumn) this.getBaseElementSpec()).isPrimaryKey()) {
            this.setImage(DBModelerUtil.PRIMARYKEY);
        } else {
            this.setImage(DBModelerUtil.FOREIGNKEY);
        }
    }
    
        
    @Override
    public String getIconPath() {
        return FOREIGNKEY_ICON_PATH;
    }

    @Override
    public Image getIcon() {
        return FOREIGNKEY;
    }

}
