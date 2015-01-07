/**
 * Copyright 2014 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.angularjs.tests.freemarker;

import org.hamcrest.core.IsNull;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.forge.addon.angularjs.TestHelpers;
import org.jboss.forge.addon.resource.Resource;
import org.jboss.forge.addon.resource.ResourceFactory;
import org.jboss.forge.addon.templates.Template;
import org.jboss.forge.addon.templates.TemplateFactory;
import org.jboss.forge.addon.templates.freemarker.FreemarkerTemplate;
import org.jboss.forge.arquillian.AddonDependency;
import org.jboss.forge.arquillian.Dependencies;
import org.jboss.forge.arquillian.archive.ForgeArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertThat;

/**
 * Tests to verify that Freemarker templates that generate JavaScript work. Verifies that the templates dont error out during
 * processing. Functional tests verify whether the generated JavaScript actually work.
 */
@RunWith(Arquillian.class)
public class FreemarkerClientTest {

    @Inject
    private ResourceFactory resourceFactory;

    @Inject
    private TemplateFactory processorFactory;

    @Deployment
    @Dependencies({
            @AddonDependency(name = "org.jboss.forge.addon:scaffold-spi"),
            @AddonDependency(name = "org.jboss.forge.addon:javaee"),
            @AddonDependency(name = "org.jboss.forge.addon:templates"),
            @AddonDependency(name = "org.jboss.forge.addon:text"),
            @AddonDependency(name = "org.jboss.forge.addon:convert"),
            @AddonDependency(name = "org.jboss.forge.addon:parser-java")
    })
    public static ForgeArchive getDeployment()
    {
        return Deployments.getDeployment();
    }

    @Test
    public void testGenerateEntityController() throws Exception {

        List<Map<String,String>> entityAttributeProperties = new ArrayList<Map<String,String>>();
        entityAttributeProperties.add(TestHelpers.ENTITY_ID_PROP);
        entityAttributeProperties.add(TestHelpers.ENTITY_VERSION_PROP);
        entityAttributeProperties.add(TestHelpers.BASIC_STRING_PROP);
        Map<String, Object> root = TestHelpers.createEntityRootmap(entityAttributeProperties);

        Resource<URL> templateResource = resourceFactory.create(getClass().getResource(Deployments.BASE_PACKAGE_PATH + Deployments.JS_ENTITY_FTL_CONTROLLER));
        Template processor = processorFactory.create(templateResource, FreemarkerTemplate.class);
        String output = processor.process(root);
        assertThat(output, IsNull.notNullValue());

        Deployments.writeToTemp(output, "controller.js");
    }

    @Test
    public void testGenerateEntityModel() throws Exception {
        List<Map<String, String>> entityAttributeProperties = new ArrayList<Map<String, String>>();
        entityAttributeProperties.add(TestHelpers.ENTITY_ID_PROP);
        entityAttributeProperties.add(TestHelpers.ENTITY_VERSION_PROP);
        entityAttributeProperties.add(TestHelpers.BASIC_STRING_PROP);
        Map<String, Object> root = TestHelpers.createEntityRootmap(entityAttributeProperties);

        Resource<URL> templateResource = resourceFactory.create(getClass().getResource(Deployments.BASE_PACKAGE_PATH + Deployments.JS_ENTITY_FTL_MODEL));
        Template processor = processorFactory.create(templateResource, FreemarkerTemplate.class);
        String output = processor.process(root);
        assertThat(output, IsNull.notNullValue());

        Deployments.writeToTemp(output, "model.js");

    }

    @Test
    public void testGenerateEntityStore() throws Exception {
        List<Map<String, String>> entityAttributeProperties = new ArrayList<Map<String, String>>();
        entityAttributeProperties.add(TestHelpers.ENTITY_ID_PROP);
        entityAttributeProperties.add(TestHelpers.ENTITY_VERSION_PROP);
        entityAttributeProperties.add(TestHelpers.BASIC_STRING_PROP);
        Map<String, Object> root = TestHelpers.createEntityRootmap(entityAttributeProperties);

        Resource<URL> templateResource = resourceFactory.create(getClass().getResource(Deployments.BASE_PACKAGE_PATH + Deployments.JS_ENTITY_FTL_STORE));
        Template processor = processorFactory.create(templateResource, FreemarkerTemplate.class);
        String output = processor.process(root);
        assertThat(output, IsNull.notNullValue());

        Deployments.writeToTemp(output, "store.js");

    }

    @Test
    public void testGenerateEntityView() throws Exception {
        List<Map<String, String>> entityAttributeProperties = new ArrayList<Map<String, String>>();
        entityAttributeProperties.add(TestHelpers.ENTITY_ID_PROP);
        entityAttributeProperties.add(TestHelpers.ENTITY_VERSION_PROP);
        entityAttributeProperties.add(TestHelpers.BASIC_STRING_PROP);
        entityAttributeProperties.add(TestHelpers.DATE_PROP);
        Map<String, Object> root = TestHelpers.createEntityRootmap(entityAttributeProperties);

        Resource<URL> templateResource = resourceFactory.create(getClass().getResource(Deployments.BASE_PACKAGE_PATH + Deployments.JS_ENTITY_FTL_VIEW));
        Template processor = processorFactory.create(templateResource, FreemarkerTemplate.class);
        String output = processor.process(root);
        assertThat(output, IsNull.notNullValue());

        Deployments.writeToTemp(output, "entityGrid.js");
    }

    @Test
    public void testGenerateViewPort() throws Exception {
        Map<String, Object> root = TestHelpers.createGlobalRootmap();

        Resource<URL> templateResource = resourceFactory.create(getClass().getResource(Deployments.BASE_PACKAGE_PATH + Deployments.VIEWPORT_FTL_JS));
        Template processor = processorFactory.create(templateResource, FreemarkerTemplate.class);
        String output = processor.process(root);
        assertThat(output, IsNull.notNullValue());

        Deployments.writeToTemp(output, "viewPort.js");

    }

    @Test
    public void testGenerateNullIdGenerator() throws Exception {
        Map<String, Object> root = TestHelpers.createGlobalRootmap();

        Resource<URL> templateResource = resourceFactory.create(getClass().getResource(Deployments.BASE_PACKAGE_PATH + Deployments.NULLID_FTL_JS));
        Template processor = processorFactory.create(templateResource, FreemarkerTemplate.class);
        String output = processor.process(root);
        assertThat(output, IsNull.notNullValue());
        Deployments.writeToTemp(output, "nullIdGenerator.js");
    }

    @Test
    public void testGenerateIndex() throws Exception {
        Map<String, Object> root = TestHelpers.createGlobalRootmap();

        Resource<URL> templateResource = resourceFactory.create(getClass().getResource(Deployments.BASE_PACKAGE_PATH + Deployments.INDEX_FTL_HTML));
        Template processor = processorFactory.create(templateResource, FreemarkerTemplate.class);
        String output = processor.process(root);
        assertThat(output, IsNull.notNullValue());
        Deployments.writeToTemp(output, "index.html");
    }

    @Test
    public void testGenerateAngularApplication() throws Exception {
        Map<String, Object> root = TestHelpers.createGlobalRootmap();

        Resource<URL> templateResource = resourceFactory.create(getClass().getResource(Deployments.BASE_PACKAGE_PATH + Deployments.MAIN_FTL_JS));
        Template processor = processorFactory.create(templateResource, FreemarkerTemplate.class);
        String output = processor.process(root);
        assertThat(output, IsNull.notNullValue());

        Deployments.writeToTemp(output, "main.js");
    }

}
