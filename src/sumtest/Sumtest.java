/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sumtest;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Enumeration;
import java.util.Set;



public class Sumtest extends JFrame implements ActionListener{
    
    
 JTextArea indoc,outdoc;
 ArrayList als;
 File fp;  
 double soc=0,avgcount,sumsen=0,meansen,rmssen;
 Hashtable hs;
 double scnt;
 
    public Sumtest()
  {

	Container con=getContentPane();
	con.setLayout(new BorderLayout());			
	con.add(addToolbar(),BorderLayout.NORTH);
	con.add(addwinSplit(),BorderLayout.CENTER);	
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	setTitle("Sum[]");
	setSize(800,600);
	setVisible(true);	
	als=new ArrayList();
	hs=new Hashtable();
  }
    
  public JPanel addToolbar()
   {
	JToolBar jtb=new JToolBar();									
				
	JButton jb4=new JButton("Document");
	jb4.addActionListener(this);
	jb4.setForeground(new Color(200,100,150));	
	jtb.add(jb4);
		
	JButton jb1=new JButton("Sentence");
	jb1.addActionListener(this);
	jb1.setForeground(new Color(200,100,150));	
	jtb.add(jb1);
		
	JButton jb2=new JButton("Stopword");
	jb2.addActionListener(this);
	jb2.setForeground(new Color(200,100,150));	
	jtb.add(jb2);


	JButton jb3=new JButton("Unique word");
	jb3.addActionListener(this);
	jb3.setForeground(new Color(200,100,150));	
	jtb.add(jb3);
			

        JButton jb5=new JButton("Stemming");
	jb5.addActionListener(this);
	jb5.setForeground(new Color(200,100,150));	
	jtb.add(jb5);


	JButton jb6=new JButton("Significant");
	jb6.addActionListener(this);
	jb6.setForeground(new Color(200,100,150));	
	jtb.add(jb6);
				

	JButton jb7=new JButton("Weight");
	jb7.addActionListener(this);
	jb7.setForeground(new Color(200,100,150));	
	jtb.add(jb7);

        JButton jb8=new JButton("Ranking");
	jb8.addActionListener(this);
	jb8.setForeground(new Color(200,100,150));	
	jtb.add(jb8); 


	JButton jb9=new JButton("Summary");
	jb9.addActionListener(this);
	jb9.setForeground(new Color(200,100,150));	
	jtb.add(jb9); 
						
	JPanel jp=new JPanel();
	jp.setLayout(new GridLayout(1,1));
	jp.add(jtb);		
	return jp;
  }


  public JSplitPane addwinSplit()
     {
	JSplitPane jsp=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	jsp.setDividerLocation(350);
	jsp.setOneTouchExpandable(true);

	indoc=new JTextArea(20,60);			
	indoc.setFont(new Font("New Times Roman",Font.PLAIN,15));
	JScrollPane js1 = new JScrollPane(indoc);
        
				
	outdoc=new JTextArea(20,60);
	outdoc.setFont(new Font("New Times Roman",Font.PLAIN,15));
	JScrollPane js2 = new JScrollPane(outdoc);		
		
	jsp.setTopComponent(js1);					
	jsp.setBottomComponent(js2);				
	return jsp;
     }


 public void setdocument(File fp) {

	try {
	    int sz=(int)fp.length();
	    byte bs[]=new byte[sz];			
	    FileInputStream fis=new FileInputStream(fp);
	    fis.read(bs);
	    indoc.setText(new String(bs));   		  			
	    fis.close(); 	
	  }

	catch(IOException ex){}
   }


public void removestopwords() {
    
    Stopword sp=new Stopword();
    Special spl=new Special();		
    for(int i=0;i<als.size(); i++) {    
	slist sl=(slist)als.get(i);    
	sl.setsrsentence(spl.remove(sl.getrawsentense()));
	sl.setsrsentence(sp.remove(sl.getsrsentence()));
    } 	
	
    outdoc.setText("");
    outdoc.setText("NO. OF SENTENCE :"+als.size() +"\n\n");
    for(int i=0;i<als.size();i++)
      {	  
	 slist sl=(slist)als.get(i);  	 
	 outdoc.setText(outdoc.getText() + "\n" + (i+1) +":  "+ sl.getsrsentence());
      }
    		        
 }


public void separatesentense(File fp)
 {	
	Sentense sc=new Sentense(fp);
	sc.separatesentense(als);									         		
	outdoc.setText("");	
	outdoc.setText("NO OF SENTENCE :"+als.size() +"\n\n");
	scnt=als.size();
	for(int i=0;i<als.size();i++)
	 {	  
	   slist sl=(slist)als.get(i);  	 
	   outdoc.setText( outdoc.getText() + "\n " + (i+1) +":  " + sl.getrawsentense());
	 }		
 }



public double difpos(String str1,String str2)
{     
  int s1=str1.length();
  int s2=str2.length(); 
  int sz=(s1 > s2 ) ? s1 : s2 ;
  int mz=(s1 < s2 ) ? s1 : s2 ;
  double dp=mz,sm=0;  
  for ( int i=0;i<mz; i++ )
   {
       if( str1.charAt(i) != str2.charAt(i) )
        {
	       dp=i+1;
               break;
	}
       else
	  sm++;	
   }   
   return(sm*(dp/sz));             
}


private void addword(String tok,int sp,int wp)
{
    wlist wl=new wlist(tok);
    wl.incrcount(sp+1,wp);    
    hs.put(tok,wl);
}
private void upword(String tok,int sp,int wp)
{    
     wlist wl=(wlist)hs.remove(tok);
     wl.incrcount(sp+1,wp);
     hs.put(tok,wl);     		
}

private void delword(Object tok)
{    
     wlist wl=(wlist)hs.remove(tok);
     if( wl.getcount() >= avgcount )
         hs.put(tok,wl);          
}


private void setwight(Object tok)
{    
     double wg=0.0;
     wlist wl=(wlist)hs.get(tok);
     double tf=wl.getcount();
     double df=wl.sentensecount(); 
     wg=tf*Math.log10(scnt/df);         
     wl.weight(wg);         	      
}


public void Uniquewords()
 {     	        
   for(int i=0;i<als.size(); i++) 
     {    
	slist sl=(slist)als.get(i);    			
	String sen=sl.getsrsentence();	
        int wc=0;
	StringTokenizer stk=new StringTokenizer(sen," ");	
	while ( stk.hasMoreElements() )
	 {			   
	   String tok=(String)stk.nextElement();
	   tok=tok.trim(); 
	   wc++;	   	
	   if(!hs.containsKey(tok) && tok.length() >=3)	              					   		    
	       addword(tok,i,wc);
	    else if(hs.containsKey(tok) )
	       upword(tok,i,wc);	         		    
	 }	         
      }      	

  outdoc.setText("No of unique words:" +hs.size() + "\n");
  Enumeration key=hs.keys();	    
  while (key.hasMoreElements() ) 
  {
    wlist wl=(wlist)hs.get(key.nextElement());
    soc= soc +wl.getcount();
    outdoc.setText(outdoc.getText() +"\n" +wl.getword() + ":  " + wl.getcount() );		      	
  }  
  avgcount= soc/hs.size();
  
}



public void  stemword(String w1,String w2)
{

  if( !hs.containsKey(w2) || !hs.containsKey(w1) )
     {
        // System.out.print("return:");
	return; 
     }
   wlist wl1=(wlist)hs.remove(w1);
   wlist wl2=(wlist)hs.remove(w2);
	
   ArrayList wp=wl2.getwordpos();
   ArrayList sp=wl2.getsentensepos();
  
	
  for(int i=0;i<wp.size();i++)
   {
    String wp2=(String)wp.get(i); 
    String sp2=(String)sp.get(i); 	
    wl1.incrcount(Integer.parseInt(wp2),Integer.parseInt(sp2));
   }

   hs.put(w1,wl1);       

}


public void stemming()
 {     	        

   int sz=hs.size();
   double wdis[][]=new double[sz][sz];
   Set s1=hs.keySet();
   Object obj[]=s1.toArray();   
  
    for(int i=0;i<sz;i++) {        
	String str1=(String) obj[i];
      for(int j=0;j<sz;j++) {
	String str2=(String) obj[j];
	if(i!=j)
	wdis[i][j]=difpos(str1,str2);		
      }
    } 

    for(int i=0;i<sz;i++) {        
	String str1=(String)obj[i];
      for(int j=0;j<sz;j++) {
	String str2=(String)obj[j];	
	if(i!=j && wdis[i][j] >= avgcount ) {              
	   stemword(str1,str2); 		
	 }	  			
      }
    } 

  outdoc.setText("No of unique words:" +hs.size() + "\n");
  Enumeration key=hs.keys();	    
  while (key.hasMoreElements() ) 
  {
    wlist wl=(wlist)hs.get(key.nextElement());
    outdoc.setText(outdoc.getText() +"\n" +wl.getword() + ":  " + wl.getcount());		      	
  }  		     
}


public void significant()
{

  Enumeration key=hs.keys();	    
  while (key.hasMoreElements() )   
     delword(key.nextElement());
    
  outdoc.setText("No of Significant words:" +hs.size() + "\n");
  key=hs.keys();	    
  while (key.hasMoreElements() ) 
  {
    wlist wl=(wlist)hs.get(key.nextElement());
    outdoc.setText(outdoc.getText() +"\n" +wl.getword() + ":  " + wl.getcount() + ":"+wl.sentensecount() );		      	
  }  		     
}


public void weight()
{    
  Enumeration key=hs.keys();	    
  while (key.hasMoreElements())     
    setwight(key.nextElement());

  outdoc.setText("Weight of Significant words:" + "\n");
  key=hs.keys();	    
  while (key.hasMoreElements() ) 
  {
    wlist wl=(wlist)hs.get(key.nextElement());
    outdoc.setText(outdoc.getText() +"\n" +wl.getword() + ":  " + wl.weight());		      	
  }  		       
}


public String ranking()
{  
 slist  sl=null;  double max=0.0;int mi=0;
  
 for(int i=0;i<als.size();i++)
  { 	
   sl=(slist)als.get(i);    			
   String sen=sl.getsrsentence();	
   Enumeration key=hs.keys();	    
   while(key.hasMoreElements())     
    {	
     String str=(String)key.nextElement();         	
     if(sen.indexOf(str) != -1 )
      {
       wlist wl=(wlist)hs.get(str); 
       sl.weight(wl.weight());      		
      } 	       	
    }    
  }	

  
  for(int i=0;i<als.size();i++) { 	
      sl=(slist)als.get(i);    			
     if( sl.weight() > max )
       {	
	max =  sl.weight();   	
	mi=i;
       }
  }

  
  outdoc.setText("\t SUMMARY \n" ); 
  String str1=sl.getrawsentense();
  sl=(slist)als.get(mi);    			    
  outdoc.setText(outdoc.getText() + sl.getrawsentense() +"\n" );
  outdoc.setText(outdoc.getText() +"\n Sentence  and Ranking "  +"\n");

  for(int i=0;i<als.size();i++) { 	
    sl=(slist)als.get(i); 
sumsen=sumsen+ Math.pow(sl.weight(),2);    
     outdoc.setText(outdoc.getText() +"\n" + (i+1) +" : " + sl.getrawsentense() + " : " + sl.weight());     
  }  	
  
  
         meansen=sumsen/als.size();
         rmssen=Math.sqrt(meansen);
  return str1;
}

public void summarize()
{

/*  
separatesentense(fp);	
removestopwords();
Uniquewords();		
stemming();
significant();	
weight();		
ranking();
*/
outdoc.setText("SUMMARY \n\n" ); 

for(int i=0;i<als.size();i++) { 	
    slist sl = (slist)als.get(i); 
    if(sl.weight()>=rmssen)
        outdoc.setText(outdoc.getText() + sl.getrawsentense() );     
        
 }




}

public void actionPerformed(ActionEvent ae)
  {
	if(ae.getActionCommand().equals("Document") )		 
	      {					         	
		JFileChooser fc=new JFileChooser();
		if(fc.showOpenDialog(this)==JFileChooser.APPROVE_OPTION)
		{
		      als.clear();	
		      hs.clear();	
		      fp=fc.getSelectedFile();
		      setdocument(fp);  						   
		}
	     }	     	
	else if(ae.getActionCommand().equals("Sentence")) 
		separatesentense(fp);	      
	else if(ae.getActionCommand().equals("Stopword")) 	
		 removestopwords();				      
	else if(ae.getActionCommand().equals("Unique word")) 
		  Uniquewords();				      
        else if(ae.getActionCommand().equals("Stemming")) 	
		  stemming();				      
	else if(ae.getActionCommand().equals("Significant")) 	
		  significant();				      
	else if(ae.getActionCommand().equals("Weight")) 	
		  weight();		
	else if(ae.getActionCommand().equals("Ranking")) 	
		  ranking();	

	else if(ae.getActionCommand().equals("Summary")) 	
		  summarize();	
			      
  }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       JFrame.setDefaultLookAndFeelDecorated(true);
     Sumtest sm=new Sumtest();
	
    }
//    
}
