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

package io.jmix.ui.component;

import javax.annotation.Nullable;
import java.util.EventObject;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Base interface for SuggestionField components.
 *
 * @param <V> value type - collection or not
 * @param <I> item type
 */
public interface SuggestionFieldComponent<V, I> extends Field<V>, Component.Focusable, HasInputPrompt,
        HasOptionStyleProvider<I>, HasFormatter<I> {

    String POPUP_AUTO_WIDTH = "auto";
    String POPUP_PARENT_WIDTH = "parent";

    /**
     * @return delay between the last key press action and async search
     */
    int getAsyncSearchDelayMs();

    /**
     * Sets delay between the last key press action and async search.
     *
     * @param asyncSearchDelayMs delay in ms
     */
    void setAsyncSearchDelayMs(int asyncSearchDelayMs);

    /**
     * @return {@link SearchExecutor} which performs search
     */
    @Nullable
    SearchExecutor<I> getSearchExecutor();

    /**
     * Sets {@link SearchExecutor} which performs search.
     *
     * @param searchExecutor SearchExecutor instance
     */
    void setSearchExecutor(@Nullable SearchExecutor<I> searchExecutor);

    /**
     * @return an ENTER press handler
     */
    @Nullable
    Consumer<EnterPressEvent> getEnterPressHandler();

    /**
     * Sets an ENTER press handler.
     *
     * @param handler an ENTER press handler to set
     */
    void setEnterPressHandler(@Nullable Consumer<EnterPressEvent> handler);

    /**
     * @return an ARROW_DOWN press handler
     */
    @Nullable
    Consumer<ArrowDownEvent> getArrowDownHandler();
    /**
     * Sets an ARROW_DOWN press handler.
     *
     * @param handler an ARROW_DOWN press handler to set
     */
    void setArrowDownHandler(@Nullable Consumer<ArrowDownEvent> handler);

    /**
     * @return min string length to perform suggestions search
     */
    int getMinSearchStringLength();

    /**
     * Sets min string length which is required to perform suggestions search.
     *
     * @param minSearchStringLength required string length to perform search
     */
    void setMinSearchStringLength(int minSearchStringLength);

    /**
     * @return limit of suggestions which will be shown
     */
    int getSuggestionsLimit();

    /**
     * Sets limit of suggestions which will be shown.
     *
     * @param suggestionsLimit integer limit value
     */
    void setSuggestionsLimit(int suggestionsLimit);

    /**
     * Show passed suggestions in popup.
     *
     * @param suggestions suggestions to show
     */
    void showSuggestions(List<I> suggestions);

    /**
     * Sets the given {@code width} to the component popup. There are two predefined settings available:
     * {@link SuggestionField#POPUP_AUTO_WIDTH} and {@link SuggestionField#POPUP_PARENT_WIDTH}.
     *
     * @param width width of the component popup
     */
    void setPopupWidth(String width);

    /**
     * @return component popup width
     */
    String getPopupWidth();

    /**
     * Custom suggestions search action interface.
     *
     * @param <E> items type
     */
    interface SearchExecutor<E> {

        /**
         * Executed on background thread.
         *
         * @param searchString search string as is
         * @param searchParams additional parameters, empty if SearchExecutor is not instance of {@link ParametrizedSearchExecutor}
         * @return list with found items
         */
        List<E> search(String searchString, Map<String, Object> searchParams);
    }

    /**
     * Extended version of {@link SuggestionField.SearchExecutor} that allows to pass parameters.
     *
     * @param <E> items type
     */
    interface ParametrizedSearchExecutor<E> extends SearchExecutor<E> {

        /**
         * Called by the execution environment in UI thread to prepare execution parameters for
         * {@link SearchExecutor#search(String, Map)}.
         *
         * @return map with parameters
         */
        Map<String, Object> getParams();
    }

    /**
     * Event is fired when the user presses the enter key when the text field is on focus.
     */
    @SuppressWarnings("rawtypes")
    class EnterPressEvent extends EventObject {

        protected String text;

        public EnterPressEvent(SuggestionFieldComponent source, String text) {
            super(source);

            this.text = text;
        }

        @Override
        public SuggestionFieldComponent getSource() {
            return (SuggestionFieldComponent) super.getSource();
        }

        public String getText() {
            return text;
        }
    }

    /**
     * Event is fired when user pressed ARROW_DOWN key without search action.
     */
    @SuppressWarnings("rawtypes")
    class ArrowDownEvent extends EventObject {

        protected String text;

        public ArrowDownEvent(SuggestionFieldComponent source, String text) {
            super(source);

            this.text = text;
        }

        @Override
        public SuggestionFieldComponent getSource() {
            return (SuggestionFieldComponent) super.getSource();
        }

        public String getText() {
            return text;
        }
    }
}
