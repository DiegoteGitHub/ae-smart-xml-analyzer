# ae-smart-xml-analyzer
Smart XML analyzer challenge
USAGE:

java -jar xml-analyzer.jar <HTML 1> <HTML 2> <Element ID to find potential match in HTML 2>

The program searches for that element ID in document 2, first get the element by ID in document 1 and tries to find the same element ID in document 2, if found stops and shows the path to the element. If not found uses the tag name of document 1 and gets all tags with the same name in document 2 and finds a potential match using two strategies. The first one is comparing every attribute=value of each potential tag of document 1 with document 2 and gives 1 point for each match. It also compares tags by text content (if present). The returned match is the element with the highest score.
