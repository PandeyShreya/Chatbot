package com.example.chatbot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.firebase.auth.FirebaseAuth;

import org.apache.commons.lang3.StringUtils;

public class HomeActivity extends AppCompatActivity {
    private List<MsgModal> messages;
    private RecyclerView recyclerView;
    private EditText editText;
    private FloatingActionButton sendButton;
    private ChatAdapter adapter;
    private GoogleSignInClient mGoogleSignInClient;
    private Map<String, String> faq = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        messages = new ArrayList<>();
        recyclerView = findViewById(R.id.idRVChats);
        editText = findViewById(R.id.idEditMsg);
        sendButton = findViewById(R.id.idSend);

        adapter = new ChatAdapter(messages,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);



        // Add predefined FAQ questions and answers to the map
        faq.put("WHERE TO APPLY FOR THE INTERNSHIP?", "Please use the link to signup for the free online internship \n"+"https://www.industryacademiacommunity.com/courses/Internships");
        faq.put("HOW TO APPLY FOR THE INTERNSHIP?", "Please use the link to signup for the free online internship \n"+"https://www.industryacademiacommunity.com/courses/Internships");
        faq.put("IS THERE ANY GROUP  FOR UPDATES", "Please join our Telegram Channel \n" + "https://t.me/+uXmD1vTLpttjN2Vl");
        faq.put("HOW TO GET UPDATES ABOUT INTERNSHIP", "Please join our Telegram Channel \n" + "https://t.me/+uXmD1vTLpttjN2Vl");
        faq.put("WHEN THE INTERNSHIP IS GOING TO START?", "The internship is starting on Saturday, 1st July 2023");
        faq.put("WHAT IS THE DIFFERENCE IN 6,8,12 WEEKS INTERNSHIPS?", "The difference in the internship duration is to provide you flexibility to accommodate your academic requirements, as many colleges do not allow their students to do internship while the academic sessions are in progress. Such students can contribute more hours and finish their internship early.\n" +
                "The problem statement for all the duration for the resp. domain will be same. The excellence awards will categorised based on the duration and projects submitted for 6 weeks will be accessed in relation to the other projects submitted for the same duration");
        faq.put("WILL THERE BE OFFER LETTERS AND CERTIFICATES?","The participants will get following certificates and letters:\n" +
                "> Appointment letter within 15 days after the interns choose the field of interest\n" +
                "> Industry Training Certificate (further to completing assessment)\n" +
                "> Experience Certificate from Cloud Counselage (on successful completion of internship)\n" +
                "> Appreciation letter/certificate ( For top performers)");
        faq.put("WHAT ARE VARIOUS DOMAINS FOR INTERNSHIP?","There are 16 IT & 7 Management domains for internship:\n" +
                "Cloud Computing,  Digital Marketing, DevOps, Human Resources, Machine Learning, Data Analytics, Artificial Intelligence, Business Research, Web Development, Java, Python,\n" +
                "Business Development, React JS, UI/UX, Node.js, Operations Management, Android Development, Quality Assurance, Flutter, Cyber Security, Product Management,  Project Management, Game Development, Blockchain, Full Stack Development, Marketing Management");
        faq.put("DOMAINS FOR INTERNSHIP?","There are 16 IT & 7 Management domains for internship:\n" +
                "Cloud Computing,  Digital Marketing, DevOps, Human Resources, Machine Learning, Data Analytics, Artificial Intelligence, Business Research, Web Development, Java, Python,\n" +
                "Business Development, React JS, UI/UX, Node.js, Operations Management, Android Development, Quality Assurance, Flutter, Cyber Security, Product Management,  Project Management, Game Development, Blockchain, Full Stack Development, Marketing Management");
        faq.put("WILL PARTICIPANTS GET ANY HELP DURING THE PROJECT?","Participants will get help related to understanding the problem statement and the project delivery process.");
        faq.put("WHEN PARTICIPANTS WILL GET THE APPOINTMENT LETTER?","We will be providing appointment letters within 15 days after the interns choose the field of interest.");
        faq.put("WHAT IS SUCCESSFUL COMPLETION OF INTERNSHIP?","The interns have to complete the tasks as per the instructions within the deadlines.");
        faq.put("CAN PARTICIPANTS INTERN IN MORE THAN ONE DOMAIN SIMULTANEOUSLY?","We encourage focusing on one field at a time as this will help yield better results.");
        faq.put("WILL PARTICIPANTS BE ABLE TO CHOOSE/CHANGE THE DOMAIN & DURATION FOR INTERNSHIP?","Participants will get a chance to choose/change the domain and duration for internship from 14th July to 16th July.");
        faq.put("INTERNSHIP WILL BE PAID OR FREE?","Internship and other IAC activities are absolutely FREE.\n" +
                "There is no cost involved for participating in the community activities (including internships) as this is a noble initiative taken up by Cloud Counselage Pvt. Ltd. in association with Gift- A-Career Foundation, to help students, get job-ready, in time!");
        faq.put("WHAT IS THE ELIGIBILITY OR SELECTION CRITERIA FOR PARTICIPATING IN THIS INTERNSHIP?","This internship is designed for students in higher education courses and any such student aspiring for IT and Management career can participate in this internship.");
        faq.put("CAN GRADUATES AND FRESHERS APPLY FOR THIS INTERNSHIP?","This internship program is designed for undergraduate students but if graduates and freshers feel it is beneficial for them then they can participate in this internship program.");
        faq.put("TIMINGS FOR INTERNSHIP","For schedule of internship please refer Internship Schedule\n" +"blob:https://cloudcounselage0-my.sharepoint.com/ba79a5f3-4bc0-4cd3-96f1-03ebb7edc0fc"
                +"\nThe interns are expected to contribute minimum 1 to 2 hours on a daily basis.");
        faq.put("DO WE REQUIRE LAPTOP SPECIFICALLY FOR THE PROJECTS?","Yes, Laptop Required.");
        faq.put("HOW TO CHOOSE THE DOMAIN?","A form will be made available to the interns to choose the domain and the duration of the internship.");
        faq.put("WILL DATA BE PROVIDED FOR DATA ANALYTICS INTERNSHIP?","Yes, the data will be provided for data analytics.");
        faq.put("What is Industry- Academia Community (IAC)?","Industry-Academia Community (IAC) is a part of 'Industry-Academia Connect' initiative of Cloud Counselage Pvt. Ltd. in association with Gift-A-Career Foundation created for Industry & Academia PAN India. This community engages and supports higher education students and freshers by providing them with 360-degree professional development and career growth opportunities through Career Vision, Career Guidance, Industry & Corporate Exposure, and Hands-on experience/remote internships on live projects. All the benefits of the community can be availed from any corner of the world as it is an online community and at no cost to the members.");
        faq.put("What is IAC?","Industry-Academia Community (IAC) is a part of 'Industry-Academia Connect' initiative of Cloud Counselage Pvt. Ltd. in association with Gift-A-Career Foundation created for Industry & Academia PAN India. This community engages and supports higher education students and freshers by providing them with 360-degree professional development and career growth opportunities through Career Vision, Career Guidance, Industry & Corporate Exposure, and Hands-on experience/remote internships on live projects. All the benefits of the community can be availed from any corner of the world as it is an online community and at no cost to the members.");
        faq.put("What are the benefits of being part of IAC?","1. Industry Exposure Workshops\n" + "2. Career Vision\n" + "3. Career Guidance\n" + "4. Industry Training\n" + "5. Internship Program\n" + "6. Industry Visits\n" +
                "7. CV & Interview Preparations\n" + "8. Soft Skills Workshops\n" + "9. Career Assessments\n" + "10. Hackathons\n" + "11. Job Placements\n" + "12. Entrepreneurship Program");
        faq.put("Benefit of IAC?","1. Industry Exposure Workshops\n" + "2. Career Vision\n" + "3. Career Guidance\n" + "4. Industry Training\n" + "5. Internship Program\n" + "6. Industry Visits\n" +
                "7. CV & Interview Preparations\n" + "8. Soft Skills Workshops\n" + "9. Career Assessments\n" + "10. Hackathons\n" + "11. Job Placements\n" + "12. Entrepreneurship Program");
        faq.put("Is it necessary to become a member of Industry-Academia Community to avail the benefits?","You agree to become a member of the community as soon as you sign up for the app or express interest to join the community. The benefits of the app and in turn, the community are only available to the community members. Moreover, there are no fees to become a member of the community.");
        faq.put("Has this program helped students earlier?","More than 90% of the community members have rated us 4 & 5 out of 5 for their overall experience of the activities. The reviews of the participant are available on our iReviews page and social media pages. The students have experienced a boost in confidence, especially during the interview, were able to channelize the interview, and have been placed in companies like Oracle, Microsoft, Capgemini, TCS, Wipro, Deloitte, etc. In terms of higher education, they were able to secure places in reputed universities in U.S., Australia, Ireland, etc.");
        faq.put("How will the internship be conducted?","This is a 6-12 weeks online internship, that will be conducted during the vacation (generally) and you can choose any of one domain from the ones that we offer. This is a guaranteed internship for all the students in the age group of 17-24 years who aspire to IT & Management careers. There will be no interviews or aptitude tests required for participating in this internship. To keep the internship focused and reduce the stress to the students we allow working on only one technology at a time. The interns who have successfully submitted the project, get an experience certificate. The interns who have performed exceptionally well and have delivered high-quality deliverables are provided with ‘letters of appreciation' as well and are facilitated in the ‘Industry Academia Excellence Awards’.");
        faq.put("How to get logged out from this app","Type 'Logout' to get logged out.");
        faq.put("Hey","Hello, How can I help you?");
        faq.put("Hii","Hello, How can I help you?");
        faq.put("Hello","Hello, How can I help you?");
        faq.put("Thank you","Pleasure to hear that! :)");
        faq.put("Bye","Bye. Have a good day!");
        faq.put("Help","How can I help you?");

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }


    private void logout(){

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

// Initialize GoogleSignInClient
        GoogleSignInClient gsc = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (googleSignInAccount != null) {
            // The user is authenticated, so you can log them out
            GoogleSignIn.getClient(this, gso).signOut()
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                        } else {
                        }
                    });
        } else {
        }

        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));


    }

    private void sendMessage() {

        String userMessage = editText.getText().toString().trim();
        if (!userMessage.isEmpty()) {

            if(userMessage.equals("Logout") || userMessage.equals("logout")){
                logout();
            }

            String bestMatch = findBestMatch(userMessage);
            String botResponse="";
            messages.add(new MsgModal(userMessage, true));
            if (bestMatch != null) {botResponse = faq.get(bestMatch);}
            else { botResponse = "I'm not sure how to answer that.";}
            messages.add(new MsgModal(botResponse, false));
            adapter.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(messages.size() - 1);
            editText.getText().clear();
        }
    }



    private String findBestMatch(String userMessage) {
        String bestMatch = null;
        double highestSimilarity = 0.0;

        for (String predefinedQuestion : faq.keySet()) {
            double similarity = calculateSimilarity(userMessage.toLowerCase(), predefinedQuestion.toLowerCase());
            if (similarity > highestSimilarity) {
                highestSimilarity = similarity;
                bestMatch = predefinedQuestion;
            }
        }
        // Set a threshold for similarity score to determine if it's a good match
        if (highestSimilarity >= 0.7) { return bestMatch;}
        else { return null; // No good match found
        }
    }
    private double calculateSimilarity(String str1, String str2) {
        int maxLength = Math.max(str1.length(), str2.length());
        int distance = StringUtils.getLevenshteinDistance(str1, str2);
        return 1.0 - (double) distance / maxLength;
    }
}




