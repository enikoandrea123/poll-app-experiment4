import { useState } from "react";
import HomeLogInComponent from "./components/HomeLogInComponent";
import CreatePoll from "./components/CreatePoll";
import VoteComponent from "./components/VoteComponent";

export default function App() {
    const [view, setView] = useState("home");
    const [user, setUser] = useState(null);

    const handleLoginSuccess = (loggedInUser) => {
        setUser(loggedInUser);
        setView("createPoll");
    };

    const handleLogout = () => {
        setUser(null);
        setView("home");
    };

    const handleGoToPublicVote = () => {
        setView("vote");
    };
    
    const handleGoToPrivateVote = () => {
        if (user) {
            setView("vote");
        } else {
            alert("Please log in to access private polls!");
            setView("home");
        }
    };

    return (
        <div className="app-container">
            {view === "home" && (
                <HomeLogInComponent
                    onLoginSuccess={handleLoginSuccess}
                    onEnterPublicVote={handleGoToPublicVote}
                />
            )}

            {view === "createPoll" && (
                <CreatePoll
                    user={user}
                    onLogout={handleLogout}
                    onGoToPrivateVote={handleGoToPrivateVote}
                />
            )}

            {view === "vote" && (
                <VoteComponent
                    user={user}
                    onLogout={handleLogout}
                    onGoToCreatePoll={() => setView("createPoll")}
                />
            )}
        </div>
    );
}