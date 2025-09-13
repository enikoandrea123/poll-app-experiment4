import { useState } from "react";
import "./CreatePoll.css";
import { HiArchiveBoxXMark } from "react-icons/hi2";

export default function CreatePoll({ onLogout, onGoToPrivateVote }) {
    const [question, setQuestion] = useState("");
    const [options, setOptions] = useState(["", ""]);
    const [isPublic, setIsPublic] = useState(true);

    const handleAddOption = () => setOptions([...options, ""]);

    const handleChangeOption = (idx, value) => {
        const updated = [...options];
        updated[idx] = value;
        setOptions(updated);
    };

    const handleDeleteOption = (idx) => {
        if (options.length > 2) {
            const updated = options.filter((_, i) => i !== idx);
            setOptions(updated);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const poll = {
            question,
            creatorId: 1,
            publishedAt: new Date().toISOString(),
            validUntil: "2026-01-01T00:00:00Z",
            isPublic,
            allowSingleVotePerUser: true,
            options: options.map((caption, idx) => ({
                caption,
                presentationOrder: idx + 1,
            })),
        };

        try {
            const res = await fetch("/polls", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(poll),
            });

            if (!res.ok) {
                const errorText = await res.text();
                console.error("Backend responded with error:", errorText);
                alert("Failed to create poll.");
                return;
            }

            setQuestion("");
            setOptions(["", ""]);
            setIsPublic(true);
            alert("Poll created!");
        } catch (err) {
            console.error("Network error:", err);
            alert("Network error");
        }
    };

    return (
        <div>
            <div className="poll-nav">
                <button onClick={onLogout} className="btn btn-secondary">
                    Logout
                </button>
                <button onClick={onGoToPrivateVote} className="btn btn-primary">
                    Private Polls
                </button>
            </div>

            <form onSubmit={handleSubmit} className="poll-form">
                <h2 className="poll-title">Create a Poll</h2>

                <input
                    type="text"
                    value={question}
                    onChange={(e) => setQuestion(e.target.value)}
                    placeholder="Enter your question"
                    className="poll-input"
                />

                <div className="poll-status">
                    <label>
                        <input
                            type="radio"
                            name="status"
                            checked={isPublic}
                            onChange={() => setIsPublic(true)}
                        />
                        Public
                    </label>
                    <label>
                        <input
                            type="radio"
                            name="status"
                            checked={!isPublic}
                            onChange={() => setIsPublic(false)}
                        />
                        Private
                    </label>
                </div>

                <div className="poll-options">
                    {options.map((opt, idx) => (
                        <div key={idx} className="poll-option-row">
                            <input
                                type="text"
                                value={opt}
                                onChange={(e) => handleChangeOption(idx, e.target.value)}
                                placeholder={`Option ${idx + 1}`}
                                className="poll-input"
                            />
                            {options.length > 2 && idx >= 2 && (
                                <button
                                    type="button"
                                    onClick={() => handleDeleteOption(idx)}
                                    className="delete-btn"
                                    title="delete this option"
                                >
                                    <HiArchiveBoxXMark />
                                </button>
                            )}
                        </div>
                    ))}
                </div>

                <div className="poll-buttons">
                    <button
                        type="button"
                        onClick={handleAddOption}
                        className="btn btn-secondary"
                    >
                        + Add Option
                    </button>

                    <button type="submit" className="btn btn-primary">
                        Create Poll
                    </button>
                </div>
            </form>
        </div>
    );
}