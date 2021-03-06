/*
 * Copyright 2016 Alireza Eskandarpour Shoferi
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

package io.github.meness.roozh.components;

import io.github.meness.roozh.Presentation;
import io.github.meness.roozh.Roozh;

/**
 * @since 2.7.2
 */
public class AmPm extends AbstractComponent {
    private String sAm = Roozh.AM;
    private String sPm = Roozh.PM;

    public AmPm() {
        super(Presentation.TEXT);
    }

    public AmPm(String am, String pm) {
        super(Presentation.TEXT);
        this.sAm = am;
        this.sPm = pm;
    }

    public String getAm() {
        return sAm;
    }

    public String getPm() {
        return sPm;
    }
}