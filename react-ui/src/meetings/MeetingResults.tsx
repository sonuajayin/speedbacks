import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import styles from './MeetingGenerator.module.css';

const MeetingResults: React.FC = () => {
    const { id } = useParams<{ id: string }>();
    const [meetings, setMeetings] = useState<any[]>([]);
    const [selectedPerson, setSelectedPerson] = useState('');

    useEffect(() => {
        const fetchMeetings = async () => {
            try {
                const response = await axios.get(`/api/meetings/${id}`);
                setMeetings(response.data); // Ensure response.data matches your backend structure
            } catch (error) {
                console.error('Error fetching meetings:', error);
            }
        };

        fetchMeetings();
    }, [id]);

    const filteredMeetings = meetings.filter(meeting =>
        meeting.member === selectedPerson || meeting.meetingWith === selectedPerson
    );

    return (
        <div>
            <h2>Generated Meetings</h2>
            <label>View meetings for:</label>
            <select value={selectedPerson} onChange={e => setSelectedPerson(e.target.value)}>
                <option value="">Select Person</option>
                {/* Populate options based on fetched meetings */}
                {Array.from(new Set(meetings.flatMap(meeting => [meeting.member, meeting.meetingWith]))).map(name => (
                    <option key={name} value={name}>
                        {name}
                    </option>
                ))}
            </select>
            {filteredMeetings && filteredMeetings.length > 0 && (
                <table className={styles.table}>
                    <thead>
                    <tr>
                        <th>Time</th>
                        <th>Member</th>
                        <th>Meeting With</th>
                        <th>Room Number</th>
                    </tr>
                    </thead>
                    <tbody>
                    {filteredMeetings.map((meeting, index) => (
                        <tr key={index}>
                            <td>{meeting.time}</td>
                            <td>{meeting.member}</td>
                            <td>{meeting.meetingWith}</td>
                            <td>{meeting.roomNumber}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}
            {!filteredMeetings || filteredMeetings.length === 0 && (
                <p>No meetings found.</p>
            )}
        </div>
    );
};

export default MeetingResults;
