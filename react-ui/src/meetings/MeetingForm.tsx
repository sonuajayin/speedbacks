import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import styles from './MeetingGenerator.module.css'; // Import CSS Module for styles

const MeetingForm: React.FC = () => {
    const [startTime, setStartTime] = useState('');
    const [interval, setInterval] = useState(5);
    const [names, setNames] = useState<string[]>(['']);
    const navigate = useNavigate();

    const handleChange = (index: number, event: React.ChangeEvent<HTMLInputElement>) => {
        const newNames = [...names];
        newNames[index] = event.target.value;
        setNames(newNames);
    };

    const addNameField = () => {
        setNames([...names, '']);
    };

    const removeNameField = (index: number) => {
        const newNames = [...names];
        newNames.splice(index, 1);
        setNames(newNames);
    };

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        const requestPayload = {
            startTime,
            intervalInMinutes: interval,
            members: names
        };

        try {
            const response = await axios.post('/api/meetings/generate', requestPayload);
            const meetingId = response.data.meetingId; // Ensure this matches the response from backend
            if (meetingId) {
                navigate(`/meetings/${meetingId}`);
            } else {
                console.error('Meeting ID is undefined');
            }
        } catch (error) {
            console.error('Error generating meetings:', error);
        }
    };

    return (
        <div>
            <form onSubmit={handleSubmit} className={styles.form}>
                <div className={styles.inputGroup}>
                    <label>Start Time:</label>
                    <input type="time" value={startTime} onChange={e => setStartTime(e.target.value)} className={styles.input} required />
                </div>
                <div className={styles.inputGroup}>
                    <label>Interval (minutes):</label>
                    <input type="number" value={interval} onChange={e => setInterval(Number(e.target.value))} className={styles.input} required />
                </div>
                {names.map((name, index) => (
                    <div key={index} className={styles.inputGroup}>
                        <input
                            type="text"
                            value={name}
                            onChange={(e) => handleChange(index, e)}
                            placeholder={`Name ${index + 1}`}
                            className={styles.input}
                            required
                        />
                        {index === names.length - 1 && (
                            <button type="button" onClick={addNameField} className={styles.addButton}>
                                + Add Name
                            </button>
                        )}
                        {index !== 0 && (
                            <button type="button" onClick={() => removeNameField(index)} className={styles.removeButton}>
                                - Remove
                            </button>
                        )}
                    </div>
                ))}
                <button type="submit" className={styles.submitButton}>
                    Generate Meetings
                </button>
            </form>
        </div>
    );
};

export default MeetingForm;
