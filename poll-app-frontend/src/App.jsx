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
        setView("vote-public");
    };
    
    const handleGoToPrivateVote = () => {
        if (user) {
            setView("vote-private");
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

            {view === "vote-public" && (
                <VoteComponent
                    mode="public"
                    user={user}
                    onGoHome={() => setView("home")}
                    onLogout={handleLogout}
                    onGoToCreatePoll={() => setView("createPoll")}
                />
            )}

            {view === "vote-private" && user && (
                <VoteComponent
                    mode="private"
                    user={user}
                    onGoHome={() => setView("home")}
                    onLogout={handleLogout}
                    onGoToCreatePoll={() => setView("createPoll")}
                />
            )}
        </div>
    );
}