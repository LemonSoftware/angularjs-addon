/**
 * Copyright 2013 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.angularjs;

import static org.jboss.forge.addon.angularjs.AngularScaffoldProvider.SCAFFOLD_DIR;

import java.util.ArrayList;
import java.util.List;

/**
 * A collection of utility methods that return collections representing groups of {@link ScaffoldResource}s for further
 * processing.
 * 
 */
public class ResourceProvider {

    //Add-on Resources

    static final String MAIN_CSS = "/style/base.css";

    static final String IMAGE_ADD = "/images/add.png";

    static final String IMAGE_DELETE = "/images/delete.png";

    static final String JS_FOLDER = "/scripts";

//    static final String EXTJS_FOLDER = "/extjs-5.0";


    //Add-on FTL resources
    public static final String NULLID_GENERATOR_FTL_JS = JS_FOLDER + "/NullIdGenerator.js.ftl";

    public static final String VIEWPORT_FTL_JS = JS_FOLDER + "/Viewport.js.ftl";

    public static final String INDEX_FTL_HTML = "/index.html.ftl";

    public static final String MAIN_FTL_JS = "/scripts/main.js.ftl";


    //project entity resources
    public  static final String JS_ENTITY_FTL_CONTROLLER = JS_FOLDER+"/controller/entityController.js.ftl";

    public  static final String JS_ENTITY_FTL_MODEL = JS_FOLDER+"/model/entityModel.js.ftl";

    public  static final String JS_ENTITY_FTL_STORE = JS_FOLDER+"/store/entityStore.js.ftl";

    public  static final String JS_ENTITY_FTL_VIEW = JS_FOLDER+"/view/entityGrid.js.ftl";

    /**
     * Provides a list of {@link org.jboss.forge.addon.ScaffoldResource}s representing static files that are to be copied upon scaffolding setup.
     *
     * @param targetDir The target directory that serves as the root directory of the destination for the generated resources to
     *        be copied to.
     * @param strategy The {@link org.jboss.forge.addon.ProcessingStrategy} to use for processing the static file. Usually this involves a strategy to copy
     *        files from a source to destination.
     * @return A list of {@link org.jboss.forge.addon.ScaffoldResource}s representing static files that are to be copied upon scaffolding setup.
     */
    public static List<ScaffoldResource> getStatics(String targetDir, ProcessingStrategy strategy) {
        List<ScaffoldResource> statics = new ArrayList<>();
        statics.add(new ScaffoldResource(SCAFFOLD_DIR + MAIN_CSS, targetDir + MAIN_CSS, strategy));
        statics.add(new ScaffoldResource(SCAFFOLD_DIR + IMAGE_ADD, targetDir + IMAGE_ADD, strategy));
        statics.add(new ScaffoldResource(SCAFFOLD_DIR + IMAGE_DELETE, targetDir + IMAGE_DELETE, strategy));
//        statics.add(new ScaffoldResource(SCAFFOLD_DIR + EXTJS_FOLDER, targetDir + EXTJS_FOLDER, strategy));

        return statics;
    }

    /**
     * Provides a list of {@link ScaffoldResource}s representing Freemarker templates that are to be processed only once for a
     * scaffold generation run.
     *
     * @param targetDir The target directory that serves as the root directory of the destination for the generated resources to
     *        be written to.
     * @param strategy The {@link ProcessingStrategy} to use for processing the static file. Usually this involves a strategy to process
     *        the file as a Freemarker template before copying it from a source to destination.
     * @return A list of {@link ScaffoldResource}s representing Freemarker templates that are to be processed only once for a
     *         scaffold generation run.
     */
    public static List<ScaffoldResource> getGlobalTemplates(String targetDir, String projectId, ProcessingStrategy strategy) {
        List<ScaffoldResource> resources = new ArrayList<>();
        resources.add(new ScaffoldResource(INDEX_FTL_HTML, targetDir + projectPathOutofTemplate(projectId, INDEX_FTL_HTML), strategy));
        resources.add(new ScaffoldResource(MAIN_FTL_JS, targetDir + projectPathOutofTemplate(projectId, MAIN_FTL_JS), strategy));
        resources.add(new ScaffoldResource(VIEWPORT_FTL_JS, targetDir + projectPathOutofTemplate(projectId, VIEWPORT_FTL_JS), strategy));
        resources.add(new ScaffoldResource(NULLID_GENERATOR_FTL_JS, targetDir + projectPathOutofTemplate(projectId, NULLID_GENERATOR_FTL_JS), strategy));

        return resources;
    }

    private static String projectPathOutofTemplate(String projectId, String ftlFile) {
        return ftlFile.substring(0, ftlFile.length() - 4).replace(JS_FOLDER, "/" + projectId);
    }

    private static String projectEntityPathOutofTemplate(String ftlFile, String projectId, String entityName) {
        ftlFile = ftlFile.replace("entity", entityName);
        return projectPathOutofTemplate(projectId, ftlFile);
    }

    /**
     * Provides a list of {@link ScaffoldResource}s representing Freemarker templates that are to be processed for every entity
     * during a scaffold generation run.
     *
     * @param targetDir The target directory that serves as the root directory of the destination for the generated resources to
     *        be written to.
     * @param entityName The name of the JPA entity
     * @param strategy The {@link ProcessingStrategy} to use for processing the static file. Usually this involves a strategy to process
     *        the file as a Freemarker template before copying it from a source to destination.
     * @return A list of {@link ScaffoldResource}s representing Freemarker templates that are to be processed for every entity
     *         during a scaffold generation run.
     */
    public static List<ScaffoldResource> getEntityTemplates(String targetDir, String projectId, String entityName, ProcessingStrategy strategy) {
        List<ScaffoldResource> resources = new ArrayList<>();
        resources.add(new ScaffoldResource(JS_ENTITY_FTL_CONTROLLER, targetDir + projectEntityPathOutofTemplate(JS_ENTITY_FTL_CONTROLLER, projectId, entityName), strategy));
        resources.add(new ScaffoldResource(JS_ENTITY_FTL_MODEL, targetDir + projectEntityPathOutofTemplate(JS_ENTITY_FTL_MODEL, projectId, entityName), strategy));
        resources.add(new ScaffoldResource(JS_ENTITY_FTL_STORE, targetDir + projectEntityPathOutofTemplate(JS_ENTITY_FTL_STORE, projectId, entityName), strategy));
        resources.add(new ScaffoldResource(JS_ENTITY_FTL_VIEW, targetDir + projectEntityPathOutofTemplate(JS_ENTITY_FTL_VIEW, projectId, entityName), strategy));
        return resources;
    }

    public static List<ScaffoldResource> getDetailTemplates(String targetDir, String projectId, String entityName, ProcessingStrategy strategy){
        List<ScaffoldResource> resources = new ArrayList<>();
        resources.add(new ScaffoldResource(JS_ENTITY_FTL_VIEW, targetDir + projectEntityPathOutofTemplate(JS_ENTITY_FTL_VIEW, projectId, entityName), strategy));
        return resources;
    }
}
