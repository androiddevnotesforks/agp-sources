/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.build.gradle.tasks;

import com.android.annotations.NonNull;
import com.android.build.api.component.impl.ComponentPropertiesImpl;
import com.android.build.gradle.internal.LoggerWrapper;
import com.android.build.gradle.internal.core.VariantDslInfo;
import com.android.build.gradle.internal.cxx.gradle.generator.CxxMetadataGenerator;
import com.android.build.gradle.internal.cxx.logging.IssueReporterLoggingEnvironment;
import com.android.build.gradle.internal.cxx.logging.ThreadLoggingEnvironment;
import com.android.build.gradle.internal.scope.InternalArtifactType;
import com.android.build.gradle.internal.tasks.UnsafeOutputsTask;
import com.android.build.gradle.internal.tasks.factory.VariantTaskCreationAction;
import com.android.builder.errors.DefaultIssueReporter;
import com.android.ide.common.process.ProcessException;
import java.io.IOException;
import java.util.concurrent.Callable;
import javax.inject.Inject;
import kotlin.Unit;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.Nested;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;

/** Task wrapper around ExternalNativeJsonGenerator. */
public abstract class ExternalNativeBuildJsonTask extends UnsafeOutputsTask {

    private Provider<CxxMetadataGenerator> generator;

    @Inject
    public ExternalNativeBuildJsonTask() {
        super("Generate json model is always run.");
    }

    @InputFiles
    @Optional
    @PathSensitive(PathSensitivity.RELATIVE)
    public abstract DirectoryProperty getRenderscriptSources();

    @Override
    protected void doTaskAction() throws ProcessException, IOException {
        try (ThreadLoggingEnvironment ignore =
                new IssueReporterLoggingEnvironment(
                        new DefaultIssueReporter(new LoggerWrapper(getLogger())))) {
            for (Callable<Unit> future : generator.get().getMetadataGenerators(false, null)) {
                future.call();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Nested
    public Provider<CxxMetadataGenerator> getExternalNativeJsonGenerator() {
        return generator;
    }

    @NonNull
    public static VariantTaskCreationAction<ExternalNativeBuildJsonTask, ComponentPropertiesImpl>
            createTaskConfigAction(
                    @NonNull Provider<CxxMetadataGenerator> generator,
                    @NonNull ComponentPropertiesImpl componentProperties) {
        return new CreationAction(componentProperties, generator);
    }

    private static class CreationAction
            extends VariantTaskCreationAction<
                    ExternalNativeBuildJsonTask, ComponentPropertiesImpl> {

        private final Provider<CxxMetadataGenerator> generator;

        private CreationAction(
                @NonNull ComponentPropertiesImpl componentProperties,
                Provider<CxxMetadataGenerator> generator) {
            super(componentProperties);
            this.generator = generator;
        }

        @NonNull
        @Override
        public String getName() {
            return computeTaskName("generateJsonModel");
        }

        @NonNull
        @Override
        public Class<ExternalNativeBuildJsonTask> getType() {
            return ExternalNativeBuildJsonTask.class;
        }

        @Override
        public void configure(
                @NonNull ExternalNativeBuildJsonTask task) {
            super.configure(task);

            task.generator = generator;
            VariantDslInfo variantDslInfo = creationConfig.getVariantDslInfo();

            if (variantDslInfo.getRenderscriptNdkModeEnabled()) {
                creationConfig
                        .getArtifacts()
                        .setTaskInputToFinalProduct(
                                InternalArtifactType.RENDERSCRIPT_SOURCE_OUTPUT_DIR.INSTANCE,
                                task.getRenderscriptSources());
            }
        }
    }
}
