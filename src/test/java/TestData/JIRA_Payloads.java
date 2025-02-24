package TestData;

public class JIRA_Payloads {
    public static String CreateIssuePayload(){
        String createIssue="{     \"fields\": {         \"project\": { \"key\": \"SCRUM\" },         \"summary\": \" are not working\",         \"issuetype\": { \"name\": \"Bug\" }     } }";
        return createIssue;
    }
}
