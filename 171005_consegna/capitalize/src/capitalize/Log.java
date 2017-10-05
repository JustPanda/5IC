package capitalize;

import java.io.*;

class Log
{
	private PrintWriter logPW;

	Log(String path)
	{
		try{
			this.logPW=new PrintWriter(new FileOutputStream(new File(path), true));
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}

	synchronized void write(String logmsg)
	{
		writeOnlyInFile(logmsg);
		System.out.println(logmsg);
	}

	synchronized void writeOnlyInFile(String logmsg)
	{
		logPW.println(logmsg);
		logPW.flush();
	}

	void close()
	{
		logPW.close();
	}
}
