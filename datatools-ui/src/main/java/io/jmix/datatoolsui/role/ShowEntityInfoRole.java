/*
 * Copyright 2020 Haulmont.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.jmix.datatoolsui.role;

import io.jmix.datatoolsui.screen.entityinfo.model.InfoValue;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.model.SecurityScope;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.security.role.annotation.SpecificPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;

/**
 * System role that grants permissions for the entity info functionality.
 */
@ResourceRole(name = "Data Tools: Entity Information window", code = ShowEntityInfoRole.CODE, scope = SecurityScope.UI)
public interface ShowEntityInfoRole {

    String CODE = "datatools-entity-info";

    @ScreenPolicy(screenIds = {"entityInfoWindow"})
    @EntityPolicy(entityClass = InfoValue.class, actions = {EntityPolicyAction.ALL})
    @EntityAttributePolicy(entityClass = InfoValue.class, action = EntityAttributePolicyAction.MODIFY, attributes = "*")
    @SpecificPolicy(resources = "datatools.ui.showEntityInfo")
    void showEntityInfo();
}
