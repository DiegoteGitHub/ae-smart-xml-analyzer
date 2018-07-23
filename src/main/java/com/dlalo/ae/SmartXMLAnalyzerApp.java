package com.dlalo.ae;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.dlalo.ae.result.MatchResult;
import com.dlalo.ae.strategy.ElementAtributesMatching;
import com.dlalo.ae.strategy.ElementTextMatching;
import com.dlalo.ae.strategy.MatchingStrategy;
import com.dlalo.ae.util.FileParser;
import com.dlalo.ae.util.SmartElementAnalyzer;

public class SmartXMLAnalyzerApp {

    private static final String DEFAULT_CHARSET_NAME = "UTF-8";
    private static final List<MatchingStrategy> MATCHING_STRATEGIES = Arrays.asList(new ElementAtributesMatching(), new ElementTextMatching());

    public static void main(String[] args) {
        final String firstHtmlPath = args[0];
        final String secondHtmlPath = args[1];
        final String elementIdToFind = args[2];

        final FileParser fileParser = new FileParser(DEFAULT_CHARSET_NAME);

        final Document originDocument = fileParser.parse(new File(firstHtmlPath));
        final Element originElement = findElementById(originDocument, elementIdToFind);

        /* First I try exact match */
        final Document secondDocument = fileParser.parse(new File(secondHtmlPath));
        final Optional<Element> secondElement = Optional.ofNullable(secondDocument.body().getElementById(elementIdToFind));
        if (secondElement.isPresent()) {
        	System.out.println("EXACT MATCH found in second document");
        	System.out.println(buildPath(secondElement.get()));
        	return;
        }
        
        /* Second I collect all elements of the same tag name */
        final Elements elementsByTag = findElementsByTag(secondDocument, originElement.tagName());

        if (elementsByTag.isEmpty()) {
            System.out.println("Document doesn't contain any potential variant.");
            return;
        }

        final SmartElementAnalyzer smartElementAnalyzer = new SmartElementAnalyzer(originElement, elementsByTag, MATCHING_STRATEGIES);
        final MatchResult potentialElement = smartElementAnalyzer.findPotentialElement();
        System.out.println(buildPath(potentialElement.getElement()));
        System.out.println("This element was chosen because of the following matches:");
        System.out.println(potentialElement.getMatches().stream().collect(Collectors.joining("\n")));
    }

    private static Element findElementById(Document source, String elementId) {
        return Optional.ofNullable(source.body().getElementById(elementId))
                .orElseThrow(() -> new RuntimeException(String.format("Element with id: '%s' is not found", elementId)));
    }

    private static Elements findElementsByTag(Document source, String tag) {
        return source.body().getElementsByTag(tag);
    }

    private static String buildPath(Element element) {
        final List<String> path = element.parents()
                .stream()
                .map(SmartXMLAnalyzerApp::formatPath)
                .collect(Collectors.toList());
        final StringBuilder builder = new StringBuilder();
        for (int i = path.size() - 1; i >= 0; i--) {
            final String s = path.get(i);
            builder.append(s).append(" > ");
        }
        builder.append(formatPath(element));
        return builder.toString();
    }

    private static String formatPath(Element element) {
        return String.format("%s[%s] ", element.tagName(), element.elementSiblingIndex());
    }
}
