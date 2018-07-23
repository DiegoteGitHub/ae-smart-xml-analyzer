package com.dlalo.ae.strategy;


import java.util.List;

import org.jsoup.nodes.Element;

public class ElementTextMatching implements MatchingStrategy {
	
	/* This matching strategy is about comparing the text value of the element (if present) and if original
	 * element and comparable element are both equal it gives one matching point */
	
    @Override
    public int match(Element origin, Element comparableElement, List<String> matches) {
        if (origin.hasText() && comparableElement.hasText()
                && origin.text().equals(comparableElement.text())) {
            matches.add("Elements text matched");
            return 1;
        }
        return 0;
    }
}
