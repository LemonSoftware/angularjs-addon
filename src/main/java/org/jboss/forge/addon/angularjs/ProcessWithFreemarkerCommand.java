/**
 * Copyright 2013 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.forge.addon.angularjs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.facets.WebResourcesFacet;
import org.jboss.forge.addon.resource.Resource;
import org.jboss.forge.addon.resource.ResourceFactory;
import org.jboss.forge.addon.templates.Template;
import org.jboss.forge.addon.templates.TemplateGenerator;
import org.jboss.forge.addon.templates.TemplateProcessor;
import org.jboss.forge.addon.templates.TemplateProcessorFactory;
import org.jboss.forge.addon.scaffold.util.ScaffoldUtil;
import org.jboss.forge.addon.templates.facets.TemplateFacet;
import org.jboss.forge.addon.templates.freemarker.FreemarkerTemplate;

/**
 * An observer for the {@link ProcessWithFreemarkerEvent} CDI event. This observer generates dynamic content based on the
 * inspection results for a JPA entity. The content is generated by Freemarker with templates serving as the basis for the
 * structure of the content.
 */
public class ProcessWithFreemarkerCommand {

    @Inject
    private ResourceFactory resourceFactory;

    @Inject
    private TemplateGenerator templateGenerator;

    @Inject
    private TemplateProcessorFactory templateProcessorFactory;

    @Inject
    private ResourceRegistry resourceRegistry;

    /**
     * Processes the Freemarker templates with a provided data model and writes the result to a pre-determined location.
     * 
     * The {@link ProcessWithFreemarkerEvent} contains all the {@link ScaffoldResource}s to be processed. The source of the
     * {@link ScaffoldResource} instance provides the location of the Freemarker template, while the destination provides the
     * location where the generated content should be written to. The event also contains a data model to be used by Freemarker
     * when processing the templates.
     * 
     * All generated resources are populated in the {@link ResourceRegistry}. The {@link ResourceRegistry} is cleared before
     * every invocation of this method.
     * 
     * @param event The event containing the {@link ScaffoldResource}s to be processed with Freemarker.
     */
    public void execute(@Observes ProcessWithFreemarkerEvent event) {
        resourceRegistry.clear();
        List<Resource<?>> resources = new ArrayList<>();
        Project project = event.getProject();

        for (ScaffoldResource projectGlobalTemplate : event.getResources()) {
            WebResourcesFacet web = project.getFacet(WebResourcesFacet.class);

            Resource<?> resource = resourceFactory.create(getClass().getResource("/scaffold/" + projectGlobalTemplate.getSource()));
            if (project.hasFacet(TemplateFacet.class)) {
                TemplateFacet templates = project.getFacet(TemplateFacet.class);
                Resource<?> templateResource = templates.getResource(projectGlobalTemplate.getSource());
                if(templateResource.exists())
                {
                    resource = templateResource;
                }
            }

            Template template = new FreemarkerTemplate(resource);
            TemplateProcessor templateProcessor = templateProcessorFactory.fromTemplate(template);
            String output = null;
            try {
                output = templateProcessor.process(event.getRoot());
            } catch (IOException ioEx) {
                throw new IllegalStateException(ioEx);
            }
            resources.add(ScaffoldUtil.createOrOverwrite(web.getWebResource(projectGlobalTemplate.getDestination()),
                    output, event.isOverwrite()));
        }
        resourceRegistry.addAll(resources);
        return;
    }

}