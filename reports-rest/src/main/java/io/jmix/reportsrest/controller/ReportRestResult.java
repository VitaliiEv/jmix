/*
 * Copyright 2021 Haulmont.
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

package io.jmix.reportsrest.controller;

import com.haulmont.yarg.reporting.ReportOutputDocument;
import com.haulmont.yarg.structure.ReportOutputType;

public class ReportRestResult {
    protected byte[] content;
    protected String documentName;
    protected ReportOutputType reportOutputType;
    protected boolean attachment;

    public ReportRestResult(ReportOutputDocument document, boolean attachment) {
        this.content = document.getContent();
        this.documentName = document.getDocumentName();
        this.reportOutputType = document.getReportOutputType();
        this.attachment = attachment;
    }

    public byte[] getContent() {
        return content;
    }

    public String getDocumentName() {
        return documentName;
    }

    public ReportOutputType getReportOutputType() {
        return reportOutputType;
    }

    public boolean isAttachment() {
        return attachment;
    }
}
