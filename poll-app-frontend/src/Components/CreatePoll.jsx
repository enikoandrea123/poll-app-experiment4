import { useState } from "react";
import "./CreatePoll.css";

export default function CreatePoll() {
    const [question, setQuestion] = useState("");
    const [options, setOptions] = useState(["", ""]);

    const handleAddOption = () => setOptions([...options, ""]);
    const handleChangeOption = (idx, value) => {
        const updated = [...options];
        updated[idx] = value;
        setOptions(updated);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const poll = {
            question,
            creatorId: 1,
            creatorName: Olaf,
            publishedAt: new Date().toISOString(),
            validUntil: "2026-01-01T00:00:00Z",
            options: options.map((caption, idx) => ({
                caption,
                presentationOrder: idx + 1,
            })),
        };

        const res = await fetch("http://localhost:8080/polls", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(poll),
        });

        if (res.ok) {
            setQuestion("");
            setOptions(["", ""]);
            alert("Poll created!");
        } else {
            alert("Failed to create poll");
        }
    };

    return (
        <form onSubmit={handleSubmit} className="poll-form">
            <h2 className="poll-title">Create a Poll</h2>

            <input
                type="text"
                value={question}
                onChange={(e) => setQuestion(e.target.value)}
                placeholder="Enter your question"
                className="poll-input"
            />

            <div className="poll-options">
                {options.map((opt, idx) => (
                    <input
                        key={idx}
                        type="text"
                        value={opt}
                        onChange={(e) => handleChangeOption(idx, e.target.value)}
                        placeholder={`Option ${idx + 1}`}
                        className="poll-input"
                    />
                ))}
            </div>

            <div className="publicity">

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
    );
}