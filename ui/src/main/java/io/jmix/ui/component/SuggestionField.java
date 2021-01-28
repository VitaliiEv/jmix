/*
 * Copyright 2019 Haulmont.
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

package io.jmix.ui.component;

import org.springframework.core.ParameterizedTypeReference;

/**
 * A filtering dropdown single-select. Items are filtered based on user input using asynchronous data loading.
 *
 * @param <V> type of value
 */
public interface SuggestionField<V> extends SuggestionFieldComponent<V, V> {

    String NAME = "suggestionField";

    static <T> ParameterizedTypeReference<SuggestionField<T>> of(Class<T> valueClass) {
        return new ParameterizedTypeReference<SuggestionField<T>>() {};
    }
}
