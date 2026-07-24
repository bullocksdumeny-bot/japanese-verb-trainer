package com.verbtrainer.dictionary;
import com.verbtrainer.conjugation.VerbClass;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import java.io.*;
import java.util.*;
@Service public class JmdictImporter {
 private final VerbRepository repo; public JmdictImporter(VerbRepository r){repo=r;}
 @Transactional public int importXml(InputStream input)throws Exception{
  List<VerbEntry> batch=new ArrayList<>(); int[] count={0}; var f=SAXParserFactory.newInstance(); f.setFeature("http://apache.org/xml/features/disallow-doctype-decl",false); f.setFeature("http://xml.org/sax/features/external-general-entities",false);
  f.newSAXParser().parse(input,new DefaultHandler(){String tag,text="";List<String> pos=new ArrayList<>(),gloss=new ArrayList<>();String keb,reb;
   public void startElement(String u,String l,String q,Attributes a){tag=q;text="";if(q.equals("entry")){pos.clear();gloss.clear();keb=reb=null;}}
   public void characters(char[] c,int s,int n){text+=new String(c,s,n);}
   public void endElement(String u,String l,String q){String x=text.trim();if(q.equals("keb")&&keb==null)keb=x;if(q.equals("reb")&&reb==null)reb=x;if(q.equals("pos"))pos.add(x);if(q.equals("gloss")&&gloss.size()<4)gloss.add(x);if(q.equals("entry")){var vc=map(pos);if(vc!=null&&reb!=null){batch.add(new VerbEntry(keb==null?reb:keb,reb,String.join("; ",gloss),vc,String.join(",",pos)));if(batch.size()>=500){repo.saveAll(batch);count[0]+=batch.size();batch.clear();}}}}
  });repo.saveAll(batch);return count[0]+batch.size();
 }
 private VerbClass map(List<String> p){String s=String.join(" ",p);if(s.contains("vs"))return VerbClass.SURU;if(s.contains("vk"))return VerbClass.KURU;if(s.contains("v1"))return VerbClass.ICHIDAN;if(s.matches(".*v5[a-z].*"))return VerbClass.GODAN;return null;}
}
