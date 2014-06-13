/////////////////////////////////////
//
//		Java程式設計
//		陳柏翰
//		資電一 s1023724
//
/////////////////////////////////////

import java.io.*;
import java.net.*;
import java.util.*;
 
public class ydl
{
    // java <url> <proxy> <port?>
	//bs8oFSAMABU <<周杰倫 晴天>>
	//1lLTdMLgEZ0 COVER
    public static void main(String[] args) throws Exception
    {
		String yt_url=new String("http://www.youtube.com/get_video_info?video_id=");
		String last=new String();
		String star=new String();
		
		Console console=System.console();
		
		URL url = new URL(yt_url+args[0]);  // 建立URL物件
        if (args.length == 4)
            setProxy(args[2], args[3]);
			
		System.out.println("============\nFetching get_video_info file...\n============");
		
        byte get=0;
        while(get==0)
		{
			try
			{
				DataInputStream in = new DataInputStream(url.openStream());// 開啟串流
				byte data;
				byte[] raw=new byte[in.available()];
				in.readFully(raw);
				String origin=new String(raw);
				String[] origin2=origin.split("&");
				
				
								
					for(int i=0;i<origin2.length;i++)
						{
							if(origin2[i].indexOf("dashmpd")!=-1)
							{
								String[] xml_url=origin2[i].split("=");
								last=URLDecoder.decode(xml_url[1],"UTF-8");
								//System.out.println(last);
								get=1;	
								break;
							}
						}
				in.close();   // 關閉串流
			}
			catch(EOFException e) { }
		}	
		
        System.out.println("============\nDone.\n============");
		
		
		try
			{
				URL temp = new URL(last);
				DataInputStream in_xml = new DataInputStream(temp.openStream());
				
				byte[] raw=new byte[in_xml.available()];
				in_xml.readFully(raw);
				String xml=new String(raw);
				
				String[] video=xml.split("http://");
				
				//System.out.println(video[video.length-1].replace("amp;","")+"\n");
				star=video[video.length-1].replace("amp;","");
				star=star.substring(0,star.indexOf("<"));
				System.out.println(star);
				
				in_xml.close();
				
				
			}
			catch(EOFException e) { }
			/////////////////////////////////////////////////
			
			
			URL video_url = new URL("http://"+star); 
			DataInputStream video_in = new DataInputStream(video_url.openStream());
			RandomAccessFile out = new RandomAccessFile("video", "rw");	
			
        try
        {
			
            System.out.println("開始下載檔案: \n\n");
            byte data;
            // 複製檔案
            while(true)
            {
				data = (byte)video_in.readByte();
				out.writeByte(data);
            } 
			
        }
        catch(EOFException e) { }
        System.out.println("檔案下載成功......");
			video_in.close();
			out.close();
    }
 
    public static void setProxy(String pProxy, String pPort) {
        Properties systemSettings = System.getProperties();
        systemSettings.put("proxySet", "true");
        systemSettings.put("proxyHost", pProxy);
        systemSettings.put("proxyPort", pPort);
        System.setProperties(systemSettings);
    }
}
		
        //System.out.println(URLDecoder.decode(text,"UTF-8"));
