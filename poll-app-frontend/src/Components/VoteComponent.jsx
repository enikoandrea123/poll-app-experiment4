import { useEffect, useState } from "react";
import "./VoteComponent.css";

export default function VoteComponent({ user, mode, onLogout, onGoToCreatePoll, onGoHome }) {
    const [polls, setPolls] = useState([]);
    const [userVotes, setUserVotes] = useState([]);

    useEffect(() => {
        const fetchPolls = () => {
            fetch("/polls")
                .then((res) => res.json())
                .then((data) => {
                    const filtered = mode === "public"
                        ? data.filter((p) => p.public)
                        : data.filter((p) => !p.public);
                    setPolls(filtered);
                })
                .catch((err) => console.error("Failed to fetch polls", err));
        };

        fetchPolls();
        const interval = setInterval(fetchPolls, 3000);
        return () => clearInterval(interval);
    }, [mode]);

    const handleVote = async (pollId, optionId) => {
        const poll = polls.find((p) => p.id === pollId);
        if (!poll.public && poll.allowSingleVotePerUser && userVotes.includes(pollId)) {
            alert("You already voted on this private poll.");
            return;
        }

        const vote = {
            voterId: user ? user.id : 0,
            optionId,
        };

        const res = await fetch(`/polls/${pollId}/votes`, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(vote),
        });

        if (res.ok) {
            if (!poll.public) {
                setUserVotes((prev) => [...prev, pollId]);
            }

            const updatedPolls = await fetch("/polls").then((r) =>
                r.json()
            );
            const filtered = mode === "public"
                ? updatedPolls.filter((p) => p.public)
                : updatedPolls.filter((p) => !p.public);
            setPolls(filtered);
        } else {
            alert("Failed to cast vote");
        }
    };

    return (
        <div className="vote-container">
            <div className="vote-header">
                {mode === "public" && (
                    <button className="btn btn-secondary back-btn" onClick={onGoHome}>
                        Back to home
                    </button>
                )}

                {mode === "private" && (
                    <div className="private-actions">
                        <button className="btn btn-secondary back-btn" onClick={onGoToCreatePoll}>
                            Create Poll
                        </button>
                        <button className="btn btn-danger logout-btn" onClick={onLogout}>
                            🚪 Logout
                        </button>
                    </div>
                )}
            </div>

            {polls.length === 0 ? (
                <p className="no-polls-msg">No polls available.</p>
            ) : (
                polls.map((poll) => (
                    <div key={poll.id} className="poll-card">
                        <div className="poll-header">
                            <h3 className="poll-title">
                                🗳️ Poll #{poll.id}
                            </h3>
                            <p className="poll-meta">
                                👤 {poll.creatorName || "Unknown"} • 🕒{" "}
                                {poll.createdAt ? new Date(poll.createdAt).toLocaleString() : "N/A"}
                            </p>
                        </div>

                        <p className="poll-question">{poll.question}</p>

                        <ul className="options-list">
                            {poll.options.map((opt) => (
                                <li key={opt.id} className="option-item">
                                    <button
                                        className="vote-btn"
                                        onClick={() => handleVote(poll.id, opt.id)}
                                    >
                                        👍 {opt.caption}
                                    </button>
                                    <span className="vote-count">{opt.voteOptionCount || 0} votes</span></li>
                            ))}
                        </ul>
                    </div>
                ))
            )}
        </div>
    );
}