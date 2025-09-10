import { useEffect, useState } from "react";

export default function VoteComponent() {
    const [polls, setPolls] = useState([]);

    useEffect(() => {
        fetchPolls();
    }, []);

    const fetchPolls = async () => {
        try {
            const res = await fetch("http://localhost:8080/polls");
            const data = await res.json();
            setPolls(data);
        } catch (err) {
            console.error("Error fetching polls:", err);
        }
    };

    const handleVote = async (pollId, optionId, voterId, voterName) => {
        setPolls((prevPolls) =>
            prevPolls.map((poll) =>
                poll.id === pollId
                    ? {
                        ...poll,
                        options: poll.options.map((opt) =>
                            opt.id === optionId
                                ? { ...opt, votes: (opt.votes ?? 0) + 1 }
                                : opt
                        ),
                    }
                    : poll
            )
        );

        try {
            const res = await fetch(`http://localhost:8080/polls/${pollId}/votes`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ voterId, optionId }),
            });

            if (!res.ok) {
                console.error("Vote failed on backend, rolling back...");
                await fetchPolls();
            }
        } catch (err) {
            console.error("Error voting:", err);
            await fetchPolls();
        }
    };

    const handleDelete = async (pollId) => {
        try {
            const res = await fetch(`http://localhost:8080/polls/${pollId}`, {
                method: "DELETE",
            });

            if (res.ok) {
                setPolls(polls.filter((p) => p.id !== pollId));
            }
        } catch (err) {
            console.error("Error deleting poll:", err);
        }
    };

    return (
        <div className="vote-component">
            {polls.map((poll) => (
                <div key={poll.id} className="poll-id">
                    <div className="header">
                        <h2 className="vote-title">Poll #{poll.id}</h2>
                        <button
                            onClick={() => handleDelete(poll.id)}
                            className="delete-button"
                        >
                            Delete Poll
                        </button>
                    </div>

                    <p className="poll-question">{poll.question}</p>

                    <ul className="voting-form">
                        {poll.options.map((opt) => (
                            <li key={opt.id} className="vote-options">
                                <button
                                    className="vote-count"
                                    onClick={() => handleVote(poll.id, opt.id, 1)}
                                >
                                   Vote 👍
                                </button>
                                <span className="vote-option-caption">{opt.caption} </span>
                                <span className="vote-counting-votes">{opt.votes ?? 0} votes</span>
                            </li>
                        ))}
                    </ul>
                </div>
            ))}
        </div>
    );
}