/*
 * Copyright Faktor Zehn GmbH.
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

import org.linkki.core.pmo.ModelObject;
import org.linkki.core.ui.element.annotation.UIComboBox;
import org.linkki.core.ui.element.annotation.UILabel;
import org.linkki.core.ui.layout.annotation.UISection;

@UISection
public class CorrectDynamicFieldsPmo {
    
	private static final String COMPONENT = "component";
	
	@ModelObject
	public Report getReport() {
		return null;
	}

	@UILabel(position = 10, label = COMPONENT, modelAttribute = "type")
	@UIComboBox(position = 10, label = COMPONENT, modelAttribute = "type")
	public void component() {
	    // model binding
	}
	
	public Class<?> getComponentComponentType() {
		return null;
	}
}