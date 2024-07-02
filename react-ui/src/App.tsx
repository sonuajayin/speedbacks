import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import MeetingForm from './meetings/MeetingForm';
import MeetingResults from './meetings/MeetingResults';
import './App.css'; // Global styles, if any

const App: React.FC = () => {
    return (
        <Router>
            <div className="App">
                <header className="App-header">
                    <h1>Speedbacks Meeting Generator</h1>
                </header>
                <main>
                    <Routes>
                        <Route path="/" element={<MeetingForm />} />
                        <Route path="/meetings/:id" element={<MeetingResults />} />
                    </Routes>
                </main>
            </div>
        </Router>
    );
};

export default App;
