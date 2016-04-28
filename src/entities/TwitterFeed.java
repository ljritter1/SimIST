/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import static java.awt.Color.blue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import twitter4j.Status;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import sandbox.KeyReader;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.util.CharacterUtil;
import java.awt.Color;
import java.awt.Container;
import views.TestFrame;
/**
 *
 * @author jakedotts
 */
public class TwitterFeed extends JFrame implements ActionListener{
    
    JFrame twitterFrame, timelineFrame, tweetFrame;
    JButton getTimeline, sendTweet, timelineBack, tweetBack, tweetPost;
    JTextArea timelineTweets, typeTweet;
    JScrollPane timelineScrollPane, tweetScrollPane;
    JLabel logoLabel;
    JPanel twitterPanel, postPanel, timelinePanel;
    ImageIcon twitterLogo;
    Color panelB = new Color(102, 117, 127);
    Color text = new Color(255, 255, 255);
    Color btnColor = new Color(85, 172, 238);
    String bird = "twitter.png";
    Container container;
//    TestFrame theTestFame;
    
    public TwitterFeed(){
        initCustomComponents();
    }
    
    public void actionPerformed(ActionEvent e){
        
        Object click = e.getSource();
        
        if(click.equals(getTimeline)){
            initTimeline();
            twitterFrame.setVisible(false);                    
        }
        
        else if(click.equals(timelineBack)){
                timelineFrame.dispose();
                twitterFrame.setVisible(true);
            }
        
        if(click.equals(sendTweet)){
            initTweet();
            twitterFrame.setVisible(false);
        }
        
        else if(click.equals(tweetPost)){
            
                tweetFrame.dispose();
                postTweet();
                twitterFrame.setVisible(true);
            }
        
        else if(click.equals(tweetBack)){
                tweetFrame.dispose();
                twitterFrame.setVisible(true);
            }
    }
    
    public void initTimeline(){
        timelineFrame = new JFrame("@SIM_IST Timeline");
        timelinePanel = new JPanel();
        
        timelinePanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        
        timelineBack = new JButton("Back");
        timelineBack.addActionListener(this);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.5;
            c.gridx = 1;
            c.gridy = 1;
            c.gridwidth = 1;
            timelinePanel.add(timelineBack, c);
        timelineBack.setForeground(btnColor);
        
        timelineTweets = new JTextArea();
        timelineTweets.setEditable(false);
        timelineScrollPane = new JScrollPane(timelineTweets);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0;
            c.ipady = 200;
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 3;
            timelinePanel.add(timelineScrollPane, c);
        
        KeyReader keys = new KeyReader();
        
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(keys.getConsumerKey())
                .setOAuthConsumerSecret(keys.getConsumerSecret())
                .setOAuthAccessToken(keys.getAccessToken())
                .setOAuthAccessTokenSecret(keys.getAccessTokenSecret());
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        try{
            System.out.println("timeline retreval worked");
        
        
        List<Status> statuses = twitter.getHomeTimeline();
        for(Status status : statuses){
            timelineTweets.append("@"+status.getUser().getScreenName()+" : "+status.getText()+"\n"+"\n");
            timelineTweets.setLineWrap(true);
            timelineTweets.setWrapStyleWord(true);
            timelineTweets.setCaretPosition(0);
            System.out.println("@"+status.getUser().getName()+" : "+status.getText());
        }
        
        
        }catch (TwitterException te){
            System.out.print("timeline retreval failed");
            te.printStackTrace();
        }
        
        timelinePanel.setBackground(panelB);
        timelineFrame.add(timelinePanel);
        timelineFrame.pack();
        timelineFrame.setSize(600, 300);
        timelineFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        timelineFrame.setLocationRelativeTo(null);
        timelineFrame.setVisible(true);
    }
    
    public void initTweet(){
        tweetFrame = new JFrame("@SIM_IST Tweet");
        postPanel = new JPanel();
        
        postPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
             
        tweetPost = new JButton("Post");
        tweetPost.addActionListener(this);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.5;
            c.gridx = 0;
            c.gridy = 1;
            postPanel.add(tweetPost, c);
        tweetPost.setForeground(btnColor);
            
        tweetBack = new JButton("Back");
        tweetBack.addActionListener(this);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.5;
            c.gridx = 2;
            c.gridy = 1;
            postPanel.add(tweetBack, c);
        tweetBack.setForeground(btnColor);
        
        typeTweet = new JTextArea();
        tweetScrollPane = new JScrollPane(typeTweet);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0;
            c.ipady = 200;
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 3;
            postPanel.add(tweetScrollPane, c);
        
        postPanel.setBackground(panelB);
        tweetFrame.add(postPanel);
        tweetFrame.pack();
        tweetFrame.setSize(600, 300);
        tweetFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        tweetFrame.setLocationRelativeTo(null);
        tweetFrame.setVisible(true);
    }
    
    public void postTweet(){
        KeyReader keyreader = new KeyReader();
        
        ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setOAuthConsumerKey(keyreader.getConsumerKey());
            cb.setOAuthConsumerSecret(keyreader.getConsumerSecret());
            cb.setOAuthAccessToken(keyreader.getAccessToken());
            cb.setOAuthAccessTokenSecret(keyreader.getAccessTokenSecret());
        
        Twitter tf = new TwitterFactory(cb.build()).getInstance();
        
        try{
            tf.updateStatus(typeTweet.getText());
            System.out.println("tweet post success");
            
        }catch(TwitterException te){
            System.out.println("tweet post failed");
            te.printStackTrace();
        }
    }
    
    public void setBird(String tweetBird){
        bird = tweetBird;
    }
    
    public String getBird(){
        return bird;
    }

    public void initCustomComponents(){
        twitterFrame = new JFrame("@SIM_IST");
        container = new Container();
        
        twitterPanel = new JPanel();
        twitterPanel.setLayout(new GridLayout(1,3));
        
        logoLabel = new JLabel(new ImageIcon("twitter.png"));
        
        getTimeline = new JButton("Get Timeline");
        getTimeline.addActionListener(this);
        getTimeline.setBackground(btnColor);
        getTimeline.setForeground(btnColor);
        
        sendTweet = new JButton("Post Tweet");
        sendTweet.addActionListener(this);
        sendTweet.setBackground(btnColor);
        sendTweet.setForeground(btnColor);
        
        twitterPanel.add(getTimeline);
        twitterPanel.add(logoLabel);
        twitterPanel.add(sendTweet);
        twitterPanel.setBackground(panelB);
        
        
        twitterFrame.add(twitterPanel);
        
        twitterFrame.pack();
        twitterFrame.setSize(600, 300);
        //twitterFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        twitterFrame.setLocationRelativeTo(null);
        twitterFrame.setVisible(true);
        container.add(twitterFrame);
    }
}
