/**
 * Copyright [2014] Gaurav Gupta
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
package org.netbeans.orm.converter.generator;

import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toList;
import org.apache.commons.lang.StringUtils;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.SourceGroup;
import static org.netbeans.jcode.jpa.util.PersistenceHelper.JTA_VALUE;
import static org.netbeans.jcode.jpa.util.PersistenceHelper.RESOURCE_LOCAL_VALUE;
import org.netbeans.modules.j2ee.persistence.dd.common.Persistence;
import org.netbeans.modules.j2ee.persistence.dd.common.PersistenceUnit;
import org.netbeans.modules.j2ee.persistence.dd.common.Properties;
import org.netbeans.modules.j2ee.persistence.dd.common.Property;
import org.netbeans.modules.j2ee.persistence.provider.ProviderUtil;
import org.netbeans.modules.j2ee.persistence.unit.PUDataObject;
import org.netbeans.modules.j2ee.persistence.wizard.Util;
import org.netbeans.orm.converter.compiler.ClassDefSnippet;
import org.netbeans.orm.converter.compiler.PersistenceXMLUnitSnippet;
import org.netbeans.orm.converter.util.ORMConvLogger;

public class PersistenceXMLGenerator {

    private static Logger logger = ORMConvLogger.getLogger(
            PersistenceXMLGenerator.class);

    private String puName = null;

    private Collection<ClassDefSnippet> classDefs = null;

    public PersistenceXMLGenerator(Collection<ClassDefSnippet> classDefs) {
        this.classDefs = classDefs;
    }

    public String getPUName() {
        return puName;
    }

    public void setPUName(String puName) {
        this.puName = puName;
    }

    //Reference : org.netbeans.modules.j2ee.persistence.wizard.unit.PersistenceUnitWizard.instantiateWProgress
    public void generatePersistenceXML(Project project, SourceGroup sourceGroup) {
        
        if(StringUtils.isEmpty(puName)){
            return;
        }

        List<String> classNames = classDefs.stream()
                .map(classDef -> classDef.getClassHelper().getFQClassName())
                .collect(toList());

        PersistenceXMLUnitSnippet persistenceXMLUnit = new PersistenceXMLUnitSnippet();
        persistenceXMLUnit.setName(puName);
        persistenceXMLUnit.setClassNames(classNames);

        try {
            // Issue Fix #5915 Start
//            String version = PersistenceUtils.getJPAVersion(project);
            PUDataObject pud = ProviderUtil.getPUDataObject(project);
            String version = pud.getPersistence().getVersion();
            boolean existFile = false;
            PersistenceUnit punit = null;
            if (pud.getPersistence().sizePersistenceUnit() != 0) {
                for (PersistenceUnit persistenceUnit_In : pud.getPersistence().getPersistenceUnit()) {
                    if (persistenceUnit_In.getName().equalsIgnoreCase(persistenceXMLUnit.getName())) {
                        punit = persistenceUnit_In;
                        existFile = true;
                        break;
                    }
                }
            }
            if (!existFile) {
                if (Persistence.VERSION_2_1.equals(version)) {
                    punit = (PersistenceUnit) new org.netbeans.modules.j2ee.persistence.dd.persistence.model_2_1.PersistenceUnit();
                } else if (Persistence.VERSION_2_0.equals(version)) {
                    punit = (PersistenceUnit) new org.netbeans.modules.j2ee.persistence.dd.persistence.model_2_0.PersistenceUnit();
                } else {//currently default 1.0
                    punit = (PersistenceUnit) new org.netbeans.modules.j2ee.persistence.dd.persistence.model_1_0.PersistenceUnit();
                }
                if (Util.isContainerManaged(project)) {
                    punit.setTransactionType(JTA_VALUE);
//                    punit.setJtaDataSource("jdbc/sample"); // custom gui will be added in future release for DataSource , JTA
                } else {
                    punit.setTransactionType(RESOURCE_LOCAL_VALUE);
                    Properties properties = punit.newProperties();
                    punit.setProperties(properties);
                    // custom gui will be added in future release for DataSource , JTA
                    Property property = properties.newProperty();
                    property.setName("javax.persistence.jdbc.url");
                    property.setValue("jdbc:derby://localhost:1527/sample");
                    properties.addProperty2(property);

                    property = properties.newProperty();
                    property.setName("javax.persistence.jdbc.password");
                    property.setValue("app");
                    properties.addProperty2(property);

                    property = properties.newProperty();
                    property.setName("javax.persistence.jdbc.driver");
                    property.setValue("org.apache.derby.jdbc.ClientDriver");
                    properties.addProperty2(property);

                    property = properties.newProperty();
                    property.setName("javax.persistence.jdbc.user");
                    property.setValue("app");
                    properties.addProperty2(property);

                }

                // Explicitly add <exclude-unlisted-classes>false</exclude-unlisted-classes>
                // See issue 142575 - desc 10, and issue 180810
                if (!Util.isJavaSE(project)) {
                    punit.setExcludeUnlistedClasses(false);
                }

                punit.setName(persistenceXMLUnit.getName());
                punit.setProvider("org.eclipse.persistence.jpa.PersistenceProvider");// custom gui will be added in future release
                ProviderUtil.setTableGeneration(punit, org.netbeans.modules.j2ee.persistence.provider.Provider.TABLE_GENERATION_CREATE, project);
                pud.addPersistenceUnit(punit);

            }
            for (String entityClass : persistenceXMLUnit.getClassNames()) { // run for both exist & non-exist-persistence
                pud.addClass(punit, entityClass, false);
            }
            pud.save();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "compiler_error", ex);
        }
    }

}
