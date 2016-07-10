/**
 * 
 */
package kit.edu.pse.goapp.server.algorithm;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import kit.edu.pse.goapp.server.daos.GpsDaoImpl;
import kit.edu.pse.goapp.server.datamodels.Event;
import kit.edu.pse.goapp.server.datamodels.GPS;
import kit.edu.pse.goapp.server.datamodels.Meeting;
import kit.edu.pse.goapp.server.datamodels.MeetingCenter;
import kit.edu.pse.goapp.server.datamodels.MeetingConfirmation;
import kit.edu.pse.goapp.server.datamodels.Participant;
import kit.edu.pse.goapp.server.datamodels.Tour;

public class MeetingGpsAlgorithm {

	public static Tour setGpsTour(Tour tour) {
		if (!isRunning(tour)) {
			return tour;
		}

		List<Participant> participants = new ArrayList<>();
		for (Participant p : tour.getParticipants()) {
			if (p.getConfirmation() == MeetingConfirmation.CONFIRMED) {
				participants.add(p);
			}
		}

		List<Participant> biggestBlob = new ArrayList<>();
		for (Participant p : participants) {
			List<Participant> smallBlob = new ArrayList<>();
			smallBlob.add(p);
			boolean abort = false;
			while (!abort) {
				abort = true;
				for (Participant inner : participants) {
                        if(!smallBlob.contains(inner))
                        {
                        	if(isInBlob(smallBlob, inner))
                        	{
                        		smallBlob.add(inner);
                        		abort = false;
                        	}
                        }
				}
			}
			if(smallBlob.size() > biggestBlob.size())
			{
				biggestBlob = new ArrayList<>(smallBlob);
			}
		}        
		List<GPS> positions = new ArrayList<>();
		for(Participant p : biggestBlob)
		{
			GpsDaoImpl dao = new GpsDaoImpl();
			dao.setUserId(p.getUser().getId());
			GPS gpsP = dao.userGetGPS();
			positions.add(gpsP);
			p.getUser().setGPS(gpsP);
		}		
		
	   GPS median = GPS.median(positions);
	   MeetingCenter center = new MeetingCenter(median);
	  center.setParticipants(biggestBlob);   			
		return tour;
	}
   
	public static boolean isInBlob(List<Participant> blob, Participant test) {
		for (Participant p : blob) {
			GpsDaoImpl dao = new GpsDaoImpl();
			dao.setUserId(p.getUser().getId());
			GPS gpsP = dao.userGetGPS();
			dao.setUserId(test.getUser().getId());
			GPS gpsTest = dao.userGetGPS();
			if (GPS.isClose(gpsP, gpsTest, Tour.MAX_CENTER_DISTANCE)) {
				return true;
			}
		}
		return false;
	}

	public static Event setGpsEvent(Event event) {
		if (!isRunning(event)) {
			return event;
		}

		for (Participant p : event.getParticipants()) {
			if (p.getConfirmation() == MeetingConfirmation.CONFIRMED) {
				GpsDaoImpl dao = new GpsDaoImpl();
				dao.setUserId(p.getUser().getId());
				GPS gps = dao.userGetGPS();
				if (GPS.isClose(gps, event.getPlace(), Event.RADIUS)) {
					p.getUser().setGPS(gps);
				}
			}
		}
		return event;
	}

	public static boolean isRunning(Meeting meeting) {
		Timestamp stamp = new Timestamp(System.currentTimeMillis());
		long halfHour = 30 * 60 * 1000;
		long duration = meeting.getDuration() * 60 * 1000;
		if ((stamp.getTime() >= meeting.getTimespamp() - halfHour)
				&& (stamp.getTime() <= meeting.getTimespamp() + duration)) {
			return true;
		}

		return false;
	}

}
