import CreatePoll from "./Components/CreatePoll";
import VoteComponent from "./Components/VoteComponent";
import HomeLogInComponent from "./Components/HomeLogInComponent.jsx"

export default function App() {
    return (
        <div className="p-6 space-y-6">
            <h1 className="text-2xl font-bold">Poll App</h1>
            <CreatePoll />
            <VoteComponent />
        </div>
    );
}