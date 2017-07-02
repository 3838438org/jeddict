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
package org.netbeans.orm.converter.generator.packageinfo;

import org.netbeans.jpa.modeler.spec.EntityMappings;
import org.netbeans.orm.converter.generator.ClassGenerator;
import org.netbeans.orm.converter.util.ClassHelper;

public class PackageInfoGenerator extends ClassGenerator<PackageInfoClassDefSnippet> {

    private EntityMappings parsedEntityMappings = null;

    public PackageInfoGenerator(EntityMappings parsedEntityMappings, String packageName) {
        super(new PackageInfoClassDefSnippet(), parsedEntityMappings.getJavaEEVersion());
        this.parsedEntityMappings = parsedEntityMappings;
        this.rootPackageName = packageName;
        this.packageName = packageName;
    }

    @Override
    public PackageInfoClassDefSnippet getClassDef() {
        ClassHelper classHelper = new ClassHelper("package-info");
        classHelper.setPackageName(packageName);
        classDef.setClassName(classHelper.getFQClassName());
        classDef.setPackageName(classHelper.getPackageName());
        classDef.setNamespace(parsedEntityMappings.getJaxbNameSpace());
        classDef.setJSONBSnippets(getJSONBCPackageSnippet(parsedEntityMappings));
        
        return classDef;
    }

}
